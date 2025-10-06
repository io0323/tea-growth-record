package logging

import play.api.Logger
import play.api.libs.json.{Json, JsValue}
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.{Inject, Singleton}

/**
 * 高度なログ管理機能を提供するクラス
 */
@Singleton
class LoggingService @Inject()() {
  
  private val logger = Logger(this.getClass)
  private val dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

  /**
   * ログレベル
   */
  sealed trait LogLevel
  case object DEBUG extends LogLevel
  case object INFO extends LogLevel
  case object WARN extends LogLevel
  case object ERROR extends LogLevel

  /**
   * ログエントリ
   */
  case class LogEntry(
    timestamp: String,
    level: String,
    message: String,
    context: Map[String, String] = Map.empty,
    exception: Option[String] = None,
    userId: Option[Long] = None,
    sessionId: Option[String] = None
  )

  /**
   * 構造化ログの出力
   */
  def logStructured(
    level: LogLevel,
    message: String,
    context: Map[String, String] = Map.empty,
    exception: Option[Throwable] = None,
    userId: Option[Long] = None,
    sessionId: Option[String] = None
  ): Unit = {
    val logEntry = LogEntry(
      timestamp = LocalDateTime.now().format(dateTimeFormatter),
      level = level.toString,
      message = message,
      context = context,
      exception = exception.map(_.getMessage),
      userId = userId,
      sessionId = sessionId
    )
    
    val jsonLog = Json.toJson(logEntry).toString()
    
    level match {
      case DEBUG => logger.debug(jsonLog)
      case INFO => logger.info(jsonLog)
      case WARN => logger.warn(jsonLog)
      case ERROR => logger.error(jsonLog)
    }
  }

  /**
   * ユーザーアクションのログ
   */
  def logUserAction(
    action: String,
    userId: Long,
    details: Map[String, String] = Map.empty
  ): Unit = {
    logStructured(
      level = INFO,
      message = s"User action: $action",
      context = details + ("action" -> action),
      userId = Some(userId)
    )
  }

  /**
   * お茶関連の操作ログ
   */
  def logTeaOperation(
    operation: String,
    teaId: Long,
    userId: Long,
    details: Map[String, String] = Map.empty
  ): Unit = {
    logStructured(
      level = INFO,
      message = s"Tea operation: $operation",
      context = details + ("operation" -> operation, "teaId" -> teaId.toString),
      userId = Some(userId)
    )
  }

  /**
   * 認証関連のログ
   */
  def logAuthEvent(
    event: String,
    email: String,
    success: Boolean,
    details: Map[String, String] = Map.empty
  ): Unit = {
    val level = if (success) INFO else WARN
    logStructured(
      level = level,
      message = s"Auth event: $event",
      context = details + ("event" -> event, "email" -> email, "success" -> success.toString)
    )
  }

  /**
   * エラーログ
   */
  def logError(
    message: String,
    exception: Throwable,
    context: Map[String, String] = Map.empty,
    userId: Option[Long] = None
  ): Unit = {
    logStructured(
      level = ERROR,
      message = message,
      context = context,
      exception = Some(exception),
      userId = userId
    )
  }

  /**
   * パフォーマンスログ
   */
  def logPerformance(
    operation: String,
    duration: Long,
    context: Map[String, String] = Map.empty
  ): Unit = {
    val level = if (duration > 1000) WARN else INFO
    logStructured(
      level = level,
      message = s"Performance: $operation took ${duration}ms",
      context = context + ("operation" -> operation, "duration" -> duration.toString)
    )
  }

  /**
   * セキュリティイベントのログ
   */
  def logSecurityEvent(
    event: String,
    severity: String,
    details: Map[String, String] = Map.empty,
    userId: Option[Long] = None
  ): Unit = {
    val level = severity.toLowerCase match {
      case "critical" | "high" => ERROR
      case "medium" => WARN
      case "low" => INFO
      case _ => INFO
    }
    
    logStructured(
      level = level,
      message = s"Security event: $event",
      context = details + ("event" -> event, "severity" -> severity),
      userId = userId
    )
  }

  /**
   * データベース操作のログ
   */
  def logDatabaseOperation(
    operation: String,
    table: String,
    duration: Long,
    success: Boolean,
    details: Map[String, String] = Map.empty
  ): Unit = {
    val level = if (success) INFO else ERROR
    logStructured(
      level = level,
      message = s"Database operation: $operation on $table",
      context = details + ("operation" -> operation, "table" -> table, "duration" -> duration.toString, "success" -> success.toString)
    )
  }

  /**
   * システム起動ログ
   */
  def logSystemStartup(): Unit = {
    logStructured(
      level = INFO,
      message = "System startup completed",
      context = Map(
        "version" -> "1.0.0",
        "environment" -> "production",
        "startupTime" -> LocalDateTime.now().format(dateTimeFormatter)
      )
    )
  }

  /**
   * システムシャットダウンログ
   */
  def logSystemShutdown(): Unit = {
    logStructured(
      level = INFO,
      message = "System shutdown initiated",
      context = Map(
        "shutdownTime" -> LocalDateTime.now().format(dateTimeFormatter)
      )
    )
  }

  /**
   * ログ統計の取得
   */
  def getLogStats(): Map[String, Any] = {
    Map(
      "totalLogs" -> "N/A", // 実際の実装ではログカウントを取得
      "errorCount" -> "N/A",
      "warningCount" -> "N/A",
      "infoCount" -> "N/A",
      "lastLogTime" -> LocalDateTime.now().format(dateTimeFormatter)
    )
  }
}
