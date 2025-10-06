package metrics

import play.api.libs.json.{Json, JsValue}
import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.concurrent.atomic.{AtomicLong, AtomicDouble}

/**
 * アプリケーションメトリクスを収集・管理するクラス
 */
@Singleton
class MetricsService @Inject()()(implicit ec: ExecutionContext) {

  private val dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

  // カウンター
  private val requestCount = new AtomicLong(0)
  private val errorCount = new AtomicLong(0)
  private val teaOperationCount = new AtomicLong(0)
  private val userLoginCount = new AtomicLong(0)
  private val cacheHitCount = new AtomicLong(0)
  private val cacheMissCount = new AtomicLong(0)

  // タイマー
  private val totalResponseTime = new AtomicLong(0)
  private val totalDatabaseTime = new AtomicLong(0)
  private val totalCacheTime = new AtomicLong(0)

  // ゲージ
  private val activeUsers = new AtomicLong(0)
  private val activeSessions = new AtomicLong(0)
  private val memoryUsage = new AtomicLong(0)

  /**
   * メトリクスデータ
   */
  case class MetricsData(
    timestamp: String,
    counters: Map[String, Long],
    timers: Map[String, Long],
    gauges: Map[String, Long],
    rates: Map[String, Double]
  )

  /**
   * リクエストカウンターを増加
   */
  def incrementRequestCount(): Unit = {
    requestCount.incrementAndGet()
  }

  /**
   * エラーカウンターを増加
   */
  def incrementErrorCount(): Unit = {
    errorCount.incrementAndGet()
  }

  /**
   * お茶操作カウンターを増加
   */
  def incrementTeaOperationCount(): Unit = {
    teaOperationCount.incrementAndGet()
  }

  /**
   * ユーザーログインカウンターを増加
   */
  def incrementUserLoginCount(): Unit = {
    userLoginCount.incrementAndGet()
  }

  /**
   * キャッシュヒットカウンターを増加
   */
  def incrementCacheHitCount(): Unit = {
    cacheHitCount.incrementAndGet()
  }

  /**
   * キャッシュミスカウンターを増加
   */
  def incrementCacheMissCount(): Unit = {
    cacheMissCount.incrementAndGet()
  }

  /**
   * レスポンス時間を記録
   */
  def recordResponseTime(timeMs: Long): Unit = {
    totalResponseTime.addAndGet(timeMs)
  }

  /**
   * データベース時間を記録
   */
  def recordDatabaseTime(timeMs: Long): Unit = {
    totalDatabaseTime.addAndGet(timeMs)
  }

  /**
   * キャッシュ時間を記録
   */
  def recordCacheTime(timeMs: Long): Unit = {
    totalCacheTime.addAndGet(timeMs)
  }

  /**
   * アクティブユーザー数を設定
   */
  def setActiveUsers(count: Long): Unit = {
    activeUsers.set(count)
  }

  /**
   * アクティブセッション数を設定
   */
  def setActiveSessions(count: Long): Unit = {
    activeSessions.set(count)
  }

  /**
   * メモリ使用量を設定
   */
  def setMemoryUsage(bytes: Long): Unit = {
    memoryUsage.set(bytes)
  }

  /**
   * パフォーマンスメトリクスを記録
   */
  def recordPerformanceMetrics(
    operation: String,
    duration: Long,
    success: Boolean
  ): Unit = {
    if (success) {
      operation match {
        case "tea_operation" => incrementTeaOperationCount()
        case "user_login" => incrementUserLoginCount()
        case _ => // その他の操作
      }
    } else {
      incrementErrorCount()
    }
  }

  /**
   * データベースメトリクスを記録
   */
  def recordDatabaseMetrics(
    operation: String,
    duration: Long,
    success: Boolean
  ): Unit = {
    recordDatabaseTime(duration)
    if (!success) {
      incrementErrorCount()
    }
  }

  /**
   * キャッシュメトリクスを記録
   */
  def recordCacheMetrics(
    operation: String,
    duration: Long,
    hit: Boolean
  ): Unit = {
    recordCacheTime(duration)
    if (hit) {
      incrementCacheHitCount()
    } else {
      incrementCacheMissCount()
    }
  }

  /**
   * 現在のメトリクスデータを取得
   */
  def getCurrentMetrics(): MetricsData = {
    val totalRequests = requestCount.get()
    val totalErrors = errorCount.get()
    val totalTeaOps = teaOperationCount.get()
    val totalLogins = userLoginCount.get()
    val totalCacheHits = cacheHitCount.get()
    val totalCacheMisses = cacheMissCount.get()

    val avgResponseTime = if (totalRequests > 0) totalResponseTime.get() / totalRequests else 0
    val avgDatabaseTime = if (totalTeaOps > 0) totalDatabaseTime.get() / totalTeaOps else 0
    val avgCacheTime = if (totalCacheHits + totalCacheMisses > 0) {
      totalCacheTime.get() / (totalCacheHits + totalCacheMisses)
    } else 0

    val errorRate = if (totalRequests > 0) totalErrors.toDouble / totalRequests else 0.0
    val cacheHitRate = if (totalCacheHits + totalCacheMisses > 0) {
      totalCacheHits.toDouble / (totalCacheHits + totalCacheMisses)
    } else 0.0

    MetricsData(
      timestamp = LocalDateTime.now().format(dateTimeFormatter),
      counters = Map(
        "requests" -> totalRequests,
        "errors" -> totalErrors,
        "tea_operations" -> totalTeaOps,
        "user_logins" -> totalLogins,
        "cache_hits" -> totalCacheHits,
        "cache_misses" -> totalCacheMisses
      ),
      timers = Map(
        "avg_response_time" -> avgResponseTime,
        "avg_database_time" -> avgDatabaseTime,
        "avg_cache_time" -> avgCacheTime
      ),
      gauges = Map(
        "active_users" -> activeUsers.get(),
        "active_sessions" -> activeSessions.get(),
        "memory_usage" -> memoryUsage.get()
      ),
      rates = Map(
        "error_rate" -> errorRate,
        "cache_hit_rate" -> cacheHitRate
      )
    )
  }

  /**
   * メトリクスをJSON形式で取得
   */
  def getMetricsAsJson(): JsValue = {
    Json.toJson(getCurrentMetrics())
  }

  /**
   * メトリクスをリセット
   */
  def resetMetrics(): Unit = {
    requestCount.set(0)
    errorCount.set(0)
    teaOperationCount.set(0)
    userLoginCount.set(0)
    cacheHitCount.set(0)
    cacheMissCount.set(0)
    totalResponseTime.set(0)
    totalDatabaseTime.set(0)
    totalCacheTime.set(0)
  }

  /**
   * ヘルスチェック用のメトリクス
   */
  def getHealthMetrics(): Map[String, Any] = {
    val metrics = getCurrentMetrics()
    Map(
      "status" -> "healthy",
      "timestamp" -> metrics.timestamp,
      "error_rate" -> metrics.rates.getOrElse("error_rate", 0.0),
      "cache_hit_rate" -> metrics.rates.getOrElse("cache_hit_rate", 0.0),
      "active_users" -> metrics.gauges.getOrElse("active_users", 0),
      "memory_usage_mb" -> (metrics.gauges.getOrElse("memory_usage", 0) / 1024 / 1024)
    )
  }

  /**
   * アラート条件のチェック
   */
  def checkAlerts(): List[String] = {
    val metrics = getCurrentMetrics()
    val alerts = scala.collection.mutable.ListBuffer[String]()

    // エラー率のチェック
    val errorRate = metrics.rates.getOrElse("error_rate", 0.0)
    if (errorRate > 0.05) { // 5%以上
      alerts += s"高エラー率: ${(errorRate * 100).toInt}%"
    }

    // レスポンス時間のチェック
    val avgResponseTime = metrics.timers.getOrElse("avg_response_time", 0)
    if (avgResponseTime > 2000) { // 2秒以上
      alerts += s"高レスポンス時間: ${avgResponseTime}ms"
    }

    // メモリ使用量のチェック
    val memoryUsage = metrics.gauges.getOrElse("memory_usage", 0)
    if (memoryUsage > 1024 * 1024 * 1024) { // 1GB以上
      alerts += s"高メモリ使用量: ${memoryUsage / 1024 / 1024}MB"
    }

    // キャッシュヒット率のチェック
    val cacheHitRate = metrics.rates.getOrElse("cache_hit_rate", 0.0)
    if (cacheHitRate < 0.8) { // 80%未満
      alerts += s"低キャッシュヒット率: ${(cacheHitRate * 100).toInt}%"
    }

    alerts.toList
  }

  /**
   * メトリクスの履歴を取得（簡略化）
   */
  def getMetricsHistory(): List[MetricsData] = {
    // 実際の実装では、時系列データベースから履歴を取得
    List(getCurrentMetrics())
  }
}
