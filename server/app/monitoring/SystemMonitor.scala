package monitoring

import scala.concurrent.{ExecutionContext, Future}
import java.time.{LocalDateTime, Duration}
import javax.inject.{Inject, Singleton}
import play.api.libs.json.{Json, JsValue}
import scala.collection.mutable
import java.lang.management.{ManagementFactory, MemoryMXBean, ThreadMXBean, OperatingSystemMXBean}
import java.util.concurrent.atomic.{AtomicLong, AtomicDouble}

/**
 * 高度なシステム監視・メトリクスシステム
 */
@Singleton
class SystemMonitor @Inject()()(implicit ec: ExecutionContext) {

  /**
   * システムメトリクス
   */
  case class SystemMetrics(
    timestamp: LocalDateTime,
    cpu: CpuMetrics,
    memory: MemoryMetrics,
    disk: DiskMetrics,
    network: NetworkMetrics,
    jvm: JvmMetrics,
    application: ApplicationMetrics
  )

  /**
   * CPUメトリクス
   */
  case class CpuMetrics(
    usagePercent: Double,
    loadAverage: Double,
    processCount: Int,
    threadCount: Int
  )

  /**
   * メモリメトリクス
   */
  case class MemoryMetrics(
    totalMemory: Long,
    usedMemory: Long,
    freeMemory: Long,
    usagePercent: Double,
    heapUsed: Long,
    heapMax: Long,
    nonHeapUsed: Long
  )

  /**
   * ディスクメトリクス
   */
  case class DiskMetrics(
    totalSpace: Long,
    usedSpace: Long,
    freeSpace: Long,
    usagePercent: Double,
    readOps: Long,
    writeOps: Long,
    readBytes: Long,
    writeBytes: Long
  )

  /**
   * ネットワークメトリクス
   */
  case class NetworkMetrics(
    bytesReceived: Long,
    bytesSent: Long,
    packetsReceived: Long,
    packetsSent: Long,
    connections: Int,
    activeConnections: Int
  )

  /**
   * JVMメトリクス
   */
  case class JvmMetrics(
    uptime: Long,
    gcCount: Long,
    gcTime: Long,
    threadCount: Int,
    daemonThreadCount: Int,
    peakThreadCount: Int,
    classCount: Long,
    loadedClassCount: Long
  )

  /**
   * アプリケーションメトリクス
   */
  case class ApplicationMetrics(
    requestCount: Long,
    errorCount: Long,
    averageResponseTime: Double,
    activeUsers: Int,
    cacheHitRate: Double,
    databaseConnections: Int,
    queueSize: Int
  )

  /**
   * アラート
   */
  case class Alert(
    id: String,
    severity: AlertSeverity,
    title: String,
    message: String,
    timestamp: LocalDateTime,
    resolved: Boolean = false,
    resolvedAt: Option[LocalDateTime] = None,
    metadata: Map[String, String] = Map.empty
  )

  sealed trait AlertSeverity
  case object Info extends AlertSeverity
  case object Warning extends AlertSeverity
  case object Error extends AlertSeverity
  case object Critical extends AlertSeverity

  /**
   * ヘルスチェック結果
   */
  case class HealthCheck(
    component: String,
    status: HealthStatus,
    message: String,
    timestamp: LocalDateTime,
    responseTime: Long,
    metadata: Map[String, String] = Map.empty
  )

  sealed trait HealthStatus
  case object Healthy extends HealthStatus
  case object Unhealthy extends HealthStatus
  case object Degraded extends HealthStatus

  // メトリクス収集用のカウンター
  private val requestCounter = new AtomicLong(0)
  private val errorCounter = new AtomicLong(0)
  private val responseTimeSum = new AtomicLong(0)
  private val responseTimeCount = new AtomicLong(0)
  private val activeUsers = new AtomicLong(0)
  private val cacheHits = new AtomicLong(0)
  private val cacheMisses = new AtomicLong(0)

  // アラート管理
  private val alerts = mutable.ListBuffer[Alert]()
  private val alertRules = mutable.Map[String, AlertRule]()

  /**
   * アラートルール
   */
  case class AlertRule(
    id: String,
    name: String,
    condition: AlertCondition,
    severity: AlertSeverity,
    enabled: Boolean = true,
    cooldown: Duration = Duration.ofMinutes(5)
  )

  sealed trait AlertCondition
  case class CpuUsageThreshold(threshold: Double) extends AlertCondition
  case class MemoryUsageThreshold(threshold: Double) extends AlertCondition
  case class DiskUsageThreshold(threshold: Double) extends AlertCondition
  case class ErrorRateThreshold(threshold: Double) extends AlertCondition
  case class ResponseTimeThreshold(threshold: Long) extends AlertCondition

  /**
   * システムメトリクスの収集
   */
  def collectSystemMetrics(): Future[SystemMetrics] = {
    val timestamp = LocalDateTime.now()
    
    for {
      cpuMetrics <- collectCpuMetrics()
      memoryMetrics <- collectMemoryMetrics()
      diskMetrics <- collectDiskMetrics()
      networkMetrics <- collectNetworkMetrics()
      jvmMetrics <- collectJvmMetrics()
      applicationMetrics <- collectApplicationMetrics()
    } yield {
      SystemMetrics(
        timestamp = timestamp,
        cpu = cpuMetrics,
        memory = memoryMetrics,
        disk = diskMetrics,
        network = networkMetrics,
        jvm = jvmMetrics,
        application = applicationMetrics
      )
    }
  }

  /**
   * CPUメトリクスの収集
   */
  private def collectCpuMetrics(): Future[CpuMetrics] = {
    Future {
      val osBean = ManagementFactory.getOperatingSystemMXBean
      val threadBean = ManagementFactory.getThreadMXBean
      
      CpuMetrics(
        usagePercent = getCpuUsage(),
        loadAverage = osBean.getSystemLoadAverage,
        processCount = getProcessCount(),
        threadCount = threadBean.getThreadCount
      )
    }
  }

  /**
   * メモリメトリクスの収集
   */
  private def collectMemoryMetrics(): Future[MemoryMetrics] = {
    Future {
      val memoryBean = ManagementFactory.getMemoryMXBean
      val heapMemory = memoryBean.getHeapMemoryUsage
      val nonHeapMemory = memoryBean.getNonHeapMemoryUsage
      val runtime = Runtime.getRuntime
      
      val totalMemory = runtime.totalMemory()
      val freeMemory = runtime.freeMemory()
      val usedMemory = totalMemory - freeMemory
      
      MemoryMetrics(
        totalMemory = totalMemory,
        usedMemory = usedMemory,
        freeMemory = freeMemory,
        usagePercent = (usedMemory.toDouble / totalMemory) * 100,
        heapUsed = heapMemory.getUsed,
        heapMax = heapMemory.getMax,
        nonHeapUsed = nonHeapMemory.getUsed
      )
    }
  }

  /**
   * ディスクメトリクスの収集
   */
  private def collectDiskMetrics(): Future[DiskMetrics] = {
    Future {
      val file = new java.io.File("/")
      val totalSpace = file.getTotalSpace
      val freeSpace = file.getFreeSpace
      val usedSpace = totalSpace - freeSpace
      
      DiskMetrics(
        totalSpace = totalSpace,
        usedSpace = usedSpace,
        freeSpace = freeSpace,
        usagePercent = (usedSpace.toDouble / totalSpace) * 100,
        readOps = 0, // 実際の実装ではOS固有のAPIを使用
        writeOps = 0,
        readBytes = 0,
        writeBytes = 0
      )
    }
  }

  /**
   * ネットワークメトリクスの収集
   */
  private def collectNetworkMetrics(): Future[NetworkMetrics] = {
    Future {
      // 実際の実装ではネットワーク統計を取得
      NetworkMetrics(
        bytesReceived = 0,
        bytesSent = 0,
        packetsReceived = 0,
        packetsSent = 0,
        connections = 0,
        activeConnections = 0
      )
    }
  }

  /**
   * JVMメトリクスの収集
   */
  private def collectJvmMetrics(): Future[JvmMetrics] = {
    Future {
      val runtimeBean = ManagementFactory.getRuntimeMXBean
      val threadBean = ManagementFactory.getThreadMXBean
      val classBean = ManagementFactory.getClassLoadingMXBean
      val gcBeans = ManagementFactory.getGarbageCollectorMXBeans
      
      val totalGcTime = gcBeans.map(_.getCollectionTime).sum
      val totalGcCount = gcBeans.map(_.getCollectionCount).sum
      
      JvmMetrics(
        uptime = runtimeBean.getUptime,
        gcCount = totalGcCount,
        gcTime = totalGcTime,
        threadCount = threadBean.getThreadCount,
        daemonThreadCount = threadBean.getDaemonThreadCount,
        peakThreadCount = threadBean.getPeakThreadCount,
        classCount = classBean.getLoadedClassCount,
        loadedClassCount = classBean.getLoadedClassCount
      )
    }
  }

  /**
   * アプリケーションメトリクスの収集
   */
  private def collectApplicationMetrics(): Future[ApplicationMetrics] = {
    Future {
      val requestCount = requestCounter.get()
      val errorCount = errorCounter.get()
      val responseTimeSumValue = responseTimeSum.get()
      val responseTimeCountValue = responseTimeCount.get()
      val averageResponseTime = if (responseTimeCountValue > 0) responseTimeSumValue.toDouble / responseTimeCountValue else 0.0
      
      val cacheHitsValue = cacheHits.get()
      val cacheMissesValue = cacheMisses.get()
      val cacheHitRate = if (cacheHitsValue + cacheMissesValue > 0) {
        cacheHitsValue.toDouble / (cacheHitsValue + cacheMissesValue)
      } else 0.0
      
      ApplicationMetrics(
        requestCount = requestCount,
        errorCount = errorCount,
        averageResponseTime = averageResponseTime,
        activeUsers = activeUsers.get().toInt,
        cacheHitRate = cacheHitRate,
        databaseConnections = 0, // 実際の実装ではデータベース接続数を取得
        queueSize = 0 // 実際の実装ではキューサイズを取得
      )
    }
  }

  /**
   * メトリクスの記録
   */
  def recordRequest(responseTime: Long): Unit = {
    requestCounter.incrementAndGet()
    responseTimeSum.addAndGet(responseTime)
    responseTimeCount.incrementAndGet()
  }

  def recordError(): Unit = {
    errorCounter.incrementAndGet()
  }

  def recordCacheHit(): Unit = {
    cacheHits.incrementAndGet()
  }

  def recordCacheMiss(): Unit = {
    cacheMisses.incrementAndGet()
  }

  def setActiveUsers(count: Int): Unit = {
    activeUsers.set(count)
  }

  /**
   * ヘルスチェックの実行
   */
  def performHealthCheck(): Future[List[HealthCheck]] = {
    val checks = List(
      checkDatabaseHealth(),
      checkMemoryHealth(),
      checkCpuHealth(),
      checkDiskHealth(),
      checkApplicationHealth()
    )
    
    Future.sequence(checks)
  }

  private def checkDatabaseHealth(): Future[HealthCheck] = {
    val startTime = System.currentTimeMillis()
    // 実際の実装ではデータベース接続をテスト
    val responseTime = System.currentTimeMillis() - startTime
    
    Future.successful(HealthCheck(
      component = "database",
      status = Healthy,
      message = "Database connection is healthy",
      timestamp = LocalDateTime.now(),
      responseTime = responseTime
    ))
  }

  private def checkMemoryHealth(): Future[HealthCheck] = {
    val runtime = Runtime.getRuntime
    val usedMemory = runtime.totalMemory() - runtime.freeMemory()
    val maxMemory = runtime.maxMemory()
    val usagePercent = (usedMemory.toDouble / maxMemory) * 100
    
    val status = usagePercent match {
      case p if p > 90 => Unhealthy
      case p if p > 80 => Degraded
      case _ => Healthy
    }
    
    Future.successful(HealthCheck(
      component = "memory",
      status = status,
      message = s"Memory usage: ${usagePercent.toInt}%",
      timestamp = LocalDateTime.now(),
      responseTime = 0,
      metadata = Map("usagePercent" -> usagePercent.toString)
    ))
  }

  private def checkCpuHealth(): Future[HealthCheck] = {
    val cpuUsage = getCpuUsage()
    val status = cpuUsage match {
      case p if p > 90 => Unhealthy
      case p if p > 80 => Degraded
      case _ => Healthy
    }
    
    Future.successful(HealthCheck(
      component = "cpu",
      status = status,
      message = s"CPU usage: ${cpuUsage.toInt}%",
      timestamp = LocalDateTime.now(),
      responseTime = 0,
      metadata = Map("usagePercent" -> cpuUsage.toString)
    ))
  }

  private def checkDiskHealth(): Future[HealthCheck] = {
    val file = new java.io.File("/")
    val totalSpace = file.getTotalSpace
    val freeSpace = file.getFreeSpace
    val usagePercent = ((totalSpace - freeSpace).toDouble / totalSpace) * 100
    
    val status = usagePercent match {
      case p if p > 90 => Unhealthy
      case p if p > 80 => Degraded
      case _ => Healthy
    }
    
    Future.successful(HealthCheck(
      component = "disk",
      status = status,
      message = s"Disk usage: ${usagePercent.toInt}%",
      timestamp = LocalDateTime.now(),
      responseTime = 0,
      metadata = Map("usagePercent" -> usagePercent.toString)
    ))
  }

  private def checkApplicationHealth(): Future[HealthCheck] = {
    val errorRate = if (requestCounter.get() > 0) {
      errorCounter.get().toDouble / requestCounter.get()
    } else 0.0
    
    val status = errorRate match {
      case r if r > 0.1 => Unhealthy
      case r if r > 0.05 => Degraded
      case _ => Healthy
    }
    
    Future.successful(HealthCheck(
      component = "application",
      status = status,
      message = s"Error rate: ${(errorRate * 100).toInt}%",
      timestamp = LocalDateTime.now(),
      responseTime = 0,
      metadata = Map("errorRate" -> errorRate.toString)
    ))
  }

  /**
   * アラートの管理
   */
  def addAlertRule(rule: AlertRule): Unit = {
    alertRules(rule.id) = rule
  }

  def checkAlerts(metrics: SystemMetrics): Future[List[Alert]] = {
    val newAlerts = mutable.ListBuffer[Alert]()
    
    alertRules.values.filter(_.enabled).foreach { rule =>
      val shouldAlert = rule.condition match {
        case CpuUsageThreshold(threshold) => metrics.cpu.usagePercent > threshold
        case MemoryUsageThreshold(threshold) => metrics.memory.usagePercent > threshold
        case DiskUsageThreshold(threshold) => metrics.disk.usagePercent > threshold
        case ErrorRateThreshold(threshold) => 
          if (metrics.application.requestCount > 0) {
            metrics.application.errorCount.toDouble / metrics.application.requestCount > threshold
          } else false
        case ResponseTimeThreshold(threshold) => metrics.application.averageResponseTime > threshold
      }
      
      if (shouldAlert) {
        val alert = Alert(
          id = generateAlertId(),
          severity = rule.severity,
          title = s"${rule.name} Alert",
          message = s"${rule.name} threshold exceeded",
          timestamp = LocalDateTime.now(),
          metadata = Map("ruleId" -> rule.id)
        )
        newAlerts += alert
        alerts += alert
      }
    }
    
    Future.successful(newAlerts.toList)
  }

  def getActiveAlerts(): Future[List[Alert]] = {
    Future.successful(alerts.filter(!_.resolved).toList)
  }

  def resolveAlert(alertId: String): Future[Boolean] = {
    alerts.find(_.id == alertId) match {
      case Some(alert) =>
        val resolvedAlert = alert.copy(
          resolved = true,
          resolvedAt = Some(LocalDateTime.now())
        )
        val index = alerts.indexWhere(_.id == alertId)
        alerts(index) = resolvedAlert
        Future.successful(true)
      case None =>
        Future.successful(false)
    }
  }

  /**
   * メトリクス履歴の取得
   */
  def getMetricsHistory(hours: Int = 24): Future[List[SystemMetrics]] = {
    // 実際の実装では時系列データベースから取得
    Future.successful(List.empty)
  }

  /**
   * システム統計の取得
   */
  def getSystemStatistics(): Future[Map[String, Any]] = {
    for {
      metrics <- collectSystemMetrics()
      healthChecks <- performHealthCheck()
      activeAlerts <- getActiveAlerts()
    } yield {
      Map(
        "metrics" -> Json.toJson(metrics),
        "healthChecks" -> healthChecks,
        "activeAlerts" -> activeAlerts.length,
        "systemUptime" -> ManagementFactory.getRuntimeMXBean.getUptime,
        "lastUpdated" -> LocalDateTime.now().toString
      )
    }
  }

  /**
   * ヘルパーメソッド
   */
  private def getCpuUsage(): Double = {
    // 簡略化されたCPU使用率の計算
    val osBean = ManagementFactory.getOperatingSystemMXBean
    osBean.getSystemLoadAverage * 100
  }

  private def getProcessCount(): Int = {
    // 簡略化されたプロセス数の取得
    1
  }

  private def generateAlertId(): String = {
    val timestamp = System.currentTimeMillis()
    val random = scala.util.Random.alphanumeric.take(8).mkString
    s"alert_${timestamp}_$random"
  }
}
