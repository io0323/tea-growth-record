package security

import models._
import services._
import scala.concurrent.{ExecutionContext, Future}
import java.time.{LocalDateTime, Duration}
import java.security.MessageDigest
import java.util.Base64
import javax.inject.{Inject, Singleton}
import javax.crypto.spec.SecretKeySpec
import javax.crypto.Cipher
import scala.util.Random

/**
 * 高度なセキュリティ機能
 */
@Singleton
class SecurityService @Inject()(
  userService: UserService,
  loggingService: LoggingService
)(implicit ec: ExecutionContext) {

  /**
   * セキュリティイベント
   */
  case class SecurityEvent(
    eventType: String,
    severity: String,
    userId: Option[Long],
    ipAddress: Option[String],
    userAgent: Option[String],
    timestamp: LocalDateTime,
    details: Map[String, String]
  )

  /**
   * 認証試行の追跡
   */
  case class AuthenticationAttempt(
    email: String,
    success: Boolean,
    timestamp: LocalDateTime,
    ipAddress: String,
    userAgent: String,
    failureReason: Option[String]
  )

  /**
   * セッション管理
   */
  case class SessionInfo(
    sessionId: String,
    userId: Long,
    createdAt: LocalDateTime,
    lastAccessedAt: LocalDateTime,
    ipAddress: String,
    userAgent: String,
    isActive: Boolean
  )

  /**
   * パスワードポリシー
   */
  case class PasswordPolicy(
    minLength: Int,
    requireUppercase: Boolean,
    requireLowercase: Boolean,
    requireNumbers: Boolean,
    requireSpecialChars: Boolean,
    maxAge: Int, // 日数
    historyCount: Int
  )

  // メモリ内のセッション管理（本番環境ではRedis等を使用）
  private val activeSessions = scala.collection.mutable.Map[String, SessionInfo]()
  private val authenticationAttempts = scala.collection.mutable.ListBuffer[AuthenticationAttempt]()
  private val passwordHistory = scala.collection.mutable.Map[Long, List[String]]()

  /**
   * デフォルトパスワードポリシー
   */
  private val defaultPasswordPolicy = PasswordPolicy(
    minLength = 8,
    requireUppercase = true,
    requireLowercase = true,
    requireNumbers = true,
    requireSpecialChars = true,
    maxAge = 90,
    historyCount = 5
  )

  /**
   * パスワードのハッシュ化
   */
  def hashPassword(password: String): String = {
    val salt = generateSalt()
    val saltedPassword = password + salt
    val digest = MessageDigest.getInstance("SHA-256")
    val hash = digest.digest(saltedPassword.getBytes("UTF-8"))
    Base64.getEncoder.encodeToString(hash) + ":" + salt
  }

  /**
   * パスワードの検証
   */
  def verifyPassword(password: String, hashedPassword: String): Boolean = {
    try {
      val parts = hashedPassword.split(":")
      if (parts.length != 2) false
      else {
        val hash = parts(0)
        val salt = parts(1)
        val saltedPassword = password + salt
        val digest = MessageDigest.getInstance("SHA-256")
        val computedHash = digest.digest(saltedPassword.getBytes("UTF-8"))
        val computedHashString = Base64.getEncoder.encodeToString(computedHash)
        hash == computedHashString
      }
    } catch {
      case _: Exception => false
    }
  }

  /**
   * ソルトの生成
   */
  private def generateSalt(): String = {
    val random = new Random()
    val saltBytes = new Array[Byte](16)
    random.nextBytes(saltBytes)
    Base64.getEncoder.encodeToString(saltBytes)
  }

  /**
   * パスワードポリシーの検証
   */
  def validatePasswordPolicy(password: String, userId: Option[Long] = None): List[String] = {
    var errors = List.empty[String]
    val policy = defaultPasswordPolicy

    if (password.length < policy.minLength) {
      errors = errors :+ s"パスワードは${policy.minLength}文字以上である必要があります"
    }

    if (policy.requireUppercase && !password.exists(_.isUpper)) {
      errors = errors :+ "パスワードには大文字を含める必要があります"
    }

    if (policy.requireLowercase && !password.exists(_.isLower)) {
      errors = errors :+ "パスワードには小文字を含める必要があります"
    }

    if (policy.requireNumbers && !password.exists(_.isDigit)) {
      errors = errors :+ "パスワードには数字を含める必要があります"
    }

    if (policy.requireSpecialChars && !password.exists("!@#$%^&*()_+-=[]{}|;:,.<>?".contains(_))) {
      errors = errors :+ "パスワードには特殊文字を含める必要があります"
    }

    // パスワード履歴のチェック
    userId.foreach { uid =>
      val history = passwordHistory.getOrElse(uid, List.empty)
      if (history.contains(password)) {
        errors = errors :+ "過去に使用したパスワードは使用できません"
      }
    }

    errors
  }

  /**
   * セッションの作成
   */
  def createSession(userId: Long, ipAddress: String, userAgent: String): String = {
    val sessionId = generateSessionId()
    val now = LocalDateTime.now()
    
    val sessionInfo = SessionInfo(
      sessionId = sessionId,
      userId = userId,
      createdAt = now,
      lastAccessedAt = now,
      ipAddress = ipAddress,
      userAgent = userAgent,
      isActive = true
    )
    
    activeSessions(sessionId) = sessionInfo
    
    loggingService.logSecurityEvent(
      event = "session_created",
      severity = "low",
      details = Map(
        "sessionId" -> sessionId,
        "userId" -> userId.toString,
        "ipAddress" -> ipAddress
      ),
      userId = Some(userId)
    )
    
    sessionId
  }

  /**
   * セッションの検証
   */
  def validateSession(sessionId: String, ipAddress: String, userAgent: String): Option[Long] = {
    activeSessions.get(sessionId) match {
      case Some(session) if session.isActive =>
        val now = LocalDateTime.now()
        val sessionAge = Duration.between(session.createdAt, now)
        val lastAccessAge = Duration.between(session.lastAccessedAt, now)
        
        // セッションの有効性チェック
        if (sessionAge.toHours > 24) { // 24時間でセッション期限切れ
          invalidateSession(sessionId)
          None
        } else if (lastAccessAge.toMinutes > 30) { // 30分でタイムアウト
          invalidateSession(sessionId)
          None
        } else if (session.ipAddress != ipAddress || session.userAgent != userAgent) {
          // IPアドレスまたはユーザーエージェントが変更された場合
          loggingService.logSecurityEvent(
            event = "session_hijack_attempt",
            severity = "high",
            details = Map(
              "sessionId" -> sessionId,
              "originalIp" -> session.ipAddress,
              "newIp" -> ipAddress,
              "originalUserAgent" -> session.userAgent,
              "newUserAgent" -> userAgent
            ),
            userId = Some(session.userId)
          )
          invalidateSession(sessionId)
          None
        } else {
          // セッションを更新
          val updatedSession = session.copy(lastAccessedAt = now)
          activeSessions(sessionId) = updatedSession
          Some(session.userId)
        }
      case _ => None
    }
  }

  /**
   * セッションの無効化
   */
  def invalidateSession(sessionId: String): Unit = {
    activeSessions.get(sessionId).foreach { session =>
      val updatedSession = session.copy(isActive = false)
      activeSessions(sessionId) = updatedSession
      
      loggingService.logSecurityEvent(
        event = "session_invalidated",
        severity = "low",
        details = Map("sessionId" -> sessionId),
        userId = Some(session.userId)
      )
    }
  }

  /**
   * セッションIDの生成
   */
  private def generateSessionId(): String = {
    val random = new Random()
    val bytes = new Array[Byte](32)
    random.nextBytes(bytes)
    Base64.getUrlEncoder.withoutPadding().encodeToString(bytes)
  }

  /**
   * 認証試行の記録
   */
  def recordAuthenticationAttempt(
    email: String,
    success: Boolean,
    ipAddress: String,
    userAgent: String,
    failureReason: Option[String] = None
  ): Unit = {
    val attempt = AuthenticationAttempt(
      email = email,
      success = success,
      timestamp = LocalDateTime.now(),
      ipAddress = ipAddress,
      userAgent = userAgent,
      failureReason = failureReason
    )
    
    authenticationAttempts += attempt
    
    val severity = if (success) "low" else "medium"
    loggingService.logAuthEvent(
      event = if (success) "login_success" else "login_failure",
      email = email,
      success = success,
      details = Map(
        "ipAddress" -> ipAddress,
        "userAgent" -> userAgent,
        "failureReason" -> failureReason.getOrElse("")
      )
    )
  }

  /**
   * ブルートフォース攻撃の検出
   */
  def detectBruteForceAttack(ipAddress: String): Boolean = {
    val recentAttempts = authenticationAttempts.filter { attempt =>
      attempt.ipAddress == ipAddress &&
      Duration.between(attempt.timestamp, LocalDateTime.now()).toMinutes < 15
    }
    
    val failedAttempts = recentAttempts.count(!_.success)
    failedAttempts >= 5 // 15分間に5回以上の失敗
  }

  /**
   * 異常なアクセスパターンの検出
   */
  def detectAnomalousAccess(userId: Long, ipAddress: String, userAgent: String): Boolean = {
    val userSessions = activeSessions.values.filter(_.userId == userId)
    
    if (userSessions.isEmpty) return false
    
    val recentSessions = userSessions.filter { session =>
      Duration.between(session.createdAt, LocalDateTime.now()).toHours < 24
    }
    
    val uniqueIps = recentSessions.map(_.ipAddress).distinct.length
    val uniqueUserAgents = recentSessions.map(_.userAgent).distinct.length
    
    // 24時間以内に3つ以上の異なるIPアドレスまたはユーザーエージェントからアクセス
    uniqueIps >= 3 || uniqueUserAgents >= 3
  }

  /**
   * データの暗号化
   */
  def encryptData(data: String, key: String): String = {
    val secretKey = new SecretKeySpec(key.getBytes("UTF-8"), "AES")
    val cipher = Cipher.getInstance("AES")
    cipher.init(Cipher.ENCRYPT_MODE, secretKey)
    val encryptedBytes = cipher.doFinal(data.getBytes("UTF-8"))
    Base64.getEncoder.encodeToString(encryptedBytes)
  }

  /**
   * データの復号化
   */
  def decryptData(encryptedData: String, key: String): String = {
    val secretKey = new SecretKeySpec(key.getBytes("UTF-8"), "AES")
    val cipher = Cipher.getInstance("AES")
    cipher.init(Cipher.DECRYPT_MODE, secretKey)
    val encryptedBytes = Base64.getDecoder.decode(encryptedData)
    val decryptedBytes = cipher.doFinal(encryptedBytes)
    new String(decryptedBytes, "UTF-8")
  }

  /**
   * セキュリティスコアの計算
   */
  def calculateSecurityScore(userId: Long): Future[Int] = {
    userService.findById(userId).map { userOpt =>
      var score = 0
      
      userOpt.foreach { user =>
        // パスワード強度スコア
        val passwordErrors = validatePasswordPolicy(user.password, Some(userId))
        score += Math.max(0, 40 - passwordErrors.length * 8)
        
        // セッション管理スコア
        val userSessions = activeSessions.values.filter(_.userId == userId)
        if (userSessions.nonEmpty) {
          val activeSessionCount = userSessions.count(_.isActive)
          score += Math.max(0, 30 - activeSessionCount * 5)
        } else {
          score += 30
        }
        
        // 認証試行スコア
        val recentAttempts = authenticationAttempts.filter { attempt =>
          attempt.email == user.email &&
          Duration.between(attempt.timestamp, LocalDateTime.now()).toDays < 7
        }
        val failedAttempts = recentAttempts.count(!_.success)
        score += Math.max(0, 30 - failedAttempts * 3)
      }
      
      Math.min(100, Math.max(0, score))
    }
  }

  /**
   * セキュリティレポートの生成
   */
  def generateSecurityReport(userId: Long): Future[Map[String, Any]] = {
    for {
      securityScore <- calculateSecurityScore(userId)
    } yield {
      val userSessions = activeSessions.values.filter(_.userId == userId)
      val recentAttempts = authenticationAttempts.filter { attempt =>
        Duration.between(attempt.timestamp, LocalDateTime.now()).toDays < 7
      }
      
      Map(
        "securityScore" -> securityScore,
        "activeSessions" -> userSessions.count(_.isActive),
        "totalSessions" -> userSessions.length,
        "recentLoginAttempts" -> recentAttempts.length,
        "failedLoginAttempts" -> recentAttempts.count(!_.success),
        "lastLogin" -> recentAttempts.filter(_.success).lastOption.map(_.timestamp.toString),
        "passwordPolicy" -> defaultPasswordPolicy,
        "recommendations" -> generateSecurityRecommendations(securityScore, userSessions.length, recentAttempts.count(!_.success))
      )
    }
  }

  /**
   * セキュリティ推奨事項の生成
   */
  private def generateSecurityRecommendations(score: Int, sessionCount: Int, failedAttempts: Int): List[String] = {
    var recommendations = List.empty[String]
    
    if (score < 70) {
      recommendations = recommendations :+ "セキュリティスコアが低いです。パスワードの強化を検討してください"
    }
    
    if (sessionCount > 3) {
      recommendations = recommendations :+ "複数のセッションがアクティブです。不要なセッションを終了してください"
    }
    
    if (failedAttempts > 3) {
      recommendations = recommendations :+ "最近のログイン失敗が多いです。アカウントの安全性を確認してください"
    }
    
    if (recommendations.isEmpty) {
      recommendations = List("セキュリティ状態は良好です。定期的なパスワード変更を継続してください")
    }
    
    recommendations
  }

  /**
   * セキュリティイベントの監視
   */
  def monitorSecurityEvents(): List[SecurityEvent] = {
    val now = LocalDateTime.now()
    val recentAttempts = authenticationAttempts.filter { attempt =>
      Duration.between(attempt.timestamp, now).toHours < 24
    }
    
    val events = scala.collection.mutable.ListBuffer[SecurityEvent]()
    
    // ブルートフォース攻撃の検出
    val ipGroups = recentAttempts.groupBy(_.ipAddress)
    ipGroups.foreach { case (ip, attempts) =>
      val failedAttempts = attempts.count(!_.success)
      if (failedAttempts >= 5) {
        events += SecurityEvent(
          eventType = "brute_force_attack",
          severity = "high",
          userId = None,
          ipAddress = Some(ip),
          userAgent = None,
          timestamp = now,
          details = Map("failedAttempts" -> failedAttempts.toString)
        )
      }
    }
    
    // 異常なアクセスパターンの検出
    activeSessions.values.foreach { session =>
      if (detectAnomalousAccess(session.userId, session.ipAddress, session.userAgent)) {
        events += SecurityEvent(
          eventType = "anomalous_access",
          severity = "medium",
          userId = Some(session.userId),
          ipAddress = Some(session.ipAddress),
          userAgent = Some(session.userAgent),
          timestamp = now,
          details = Map("sessionId" -> session.sessionId)
        )
      }
    }
    
    events.toList
  }
}
