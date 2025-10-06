package config

import play.api.Configuration
import play.api.libs.json.{Json, OFormat}
import javax.inject.{Inject, Singleton}

/**
 * アプリケーション設定を管理するクラス
 */
@Singleton
class AppConfig @Inject()(configuration: Configuration) {

  /**
   * データベース設定
   */
  case class DatabaseConfig(
    url: String,
    driver: String,
    username: String,
    password: String,
    maxConnections: Int,
    minConnections: Int
  )

  /**
   * メール設定
   */
  case class EmailConfig(
    host: String,
    port: Int,
    username: String,
    password: String,
    fromAddress: String,
    useTls: Boolean
  )

  /**
   * セキュリティ設定
   */
  case class SecurityConfig(
    secretKey: String,
    sessionTimeout: Int,
    passwordMinLength: Int,
    maxLoginAttempts: Int,
    lockoutDuration: Int
  )

  /**
   * お茶管理設定
   */
  case class TeaConfig(
    maxTeasPerUser: Int,
    defaultTeaTypes: List[String],
    supportedImageFormats: List[String],
    maxImageSize: Long,
    autoArchiveDays: Int
  )

  /**
   * データベース設定を取得
   */
  def getDatabaseConfig: DatabaseConfig = {
    DatabaseConfig(
      url = configuration.get[String]("db.default.url"),
      driver = configuration.get[String]("db.default.driver"),
      username = configuration.get[String]("db.default.username"),
      password = configuration.get[String]("db.default.password"),
      maxConnections = configuration.get[Int]("db.default.maxConnections"),
      minConnections = configuration.get[Int]("db.default.minConnections")
    )
  }

  /**
   * メール設定を取得
   */
  def getEmailConfig: EmailConfig = {
    EmailConfig(
      host = configuration.get[String]("mail.host"),
      port = configuration.get[Int]("mail.port"),
      username = configuration.get[String]("mail.username"),
      password = configuration.get[String]("mail.password"),
      fromAddress = configuration.get[String]("mail.from"),
      useTls = configuration.get[Boolean]("mail.useTls")
    )
  }

  /**
   * セキュリティ設定を取得
   */
  def getSecurityConfig: SecurityConfig = {
    SecurityConfig(
      secretKey = configuration.get[String]("play.http.secret.key"),
      sessionTimeout = configuration.get[Int]("session.timeout"),
      passwordMinLength = configuration.get[Int]("security.password.minLength"),
      maxLoginAttempts = configuration.get[Int]("security.login.maxAttempts"),
      lockoutDuration = configuration.get[Int]("security.login.lockoutDuration")
    )
  }

  /**
   * お茶管理設定を取得
   */
  def getTeaConfig: TeaConfig = {
    TeaConfig(
      maxTeasPerUser = configuration.get[Int]("tea.maxPerUser"),
      defaultTeaTypes = configuration.get[Seq[String]]("tea.defaultTypes").toList,
      supportedImageFormats = configuration.get[Seq[String]]("tea.image.formats").toList,
      maxImageSize = configuration.get[Long]("tea.image.maxSize"),
      autoArchiveDays = configuration.get[Int]("tea.autoArchive.days")
    )
  }

  /**
   * 設定値の検証
   */
  def validateConfig(): List[String] = {
    val errors = scala.collection.mutable.ListBuffer[String]()
    
    try {
      val dbConfig = getDatabaseConfig
      if (dbConfig.url.isEmpty) {
        errors += "データベースURLが設定されていません"
      }
      if (dbConfig.maxConnections <= 0) {
        errors += "最大接続数は1以上である必要があります"
      }
    } catch {
      case e: Exception => errors += s"データベース設定エラー: ${e.getMessage}"
    }
    
    try {
      val emailConfig = getEmailConfig
      if (emailConfig.host.isEmpty) {
        errors += "メールホストが設定されていません"
      }
      if (emailConfig.port <= 0 || emailConfig.port > 65535) {
        errors += "メールポートは1-65535の範囲である必要があります"
      }
    } catch {
      case e: Exception => errors += s"メール設定エラー: ${e.getMessage}"
    }
    
    try {
      val securityConfig = getSecurityConfig
      if (securityConfig.secretKey.length < 32) {
        errors += "セキュリティキーは32文字以上である必要があります"
      }
      if (securityConfig.passwordMinLength < 8) {
        errors += "パスワード最小長は8文字以上である必要があります"
      }
    } catch {
      case e: Exception => errors += s"セキュリティ設定エラー: ${e.getMessage}"
    }
    
    try {
      val teaConfig = getTeaConfig
      if (teaConfig.maxTeasPerUser <= 0) {
        errors += "ユーザーあたりの最大お茶数は1以上である必要があります"
      }
      if (teaConfig.maxImageSize <= 0) {
        errors += "最大画像サイズは1バイト以上である必要があります"
      }
    } catch {
      case e: Exception => errors += s"お茶管理設定エラー: ${e.getMessage}"
    }
    
    errors.toList
  }

  /**
   * 設定の概要を取得
   */
  def getConfigSummary: Map[String, Any] = {
    Map(
      "database" -> Map(
        "url" -> configuration.get[String]("db.default.url"),
        "driver" -> configuration.get[String]("db.default.driver"),
        "maxConnections" -> configuration.get[Int]("db.default.maxConnections")
      ),
      "email" -> Map(
        "host" -> configuration.get[String]("mail.host"),
        "port" -> configuration.get[Int]("mail.port"),
        "useTls" -> configuration.get[Boolean]("mail.useTls")
      ),
      "security" -> Map(
        "sessionTimeout" -> configuration.get[Int]("session.timeout"),
        "passwordMinLength" -> configuration.get[Int]("security.password.minLength")
      ),
      "tea" -> Map(
        "maxPerUser" -> configuration.get[Int]("tea.maxPerUser"),
        "defaultTypes" -> configuration.get[Seq[String]]("tea.defaultTypes"),
        "maxImageSize" -> configuration.get[Long]("tea.image.maxSize")
      )
    )
  }
}
