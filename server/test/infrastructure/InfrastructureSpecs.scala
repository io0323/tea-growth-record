package test.infrastructure

import org.scalatest.concurrent.ScalaFutures
import org.scalatest.time.{Millis, Seconds, Span}
import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneAppPerTest
import play.api.test.Helpers._
import play.api.test._
import repositories._
import api._
import workflow._
import monitoring._
import scala.concurrent.ExecutionContext

/**
 * AdvancedRepositoryのテスト
 */
class AdvancedRepositorySpec extends PlaySpec with GuiceOneAppPerTest with ScalaFutures {

  implicit val defaultPatience = PatienceConfig(timeout = Span(5, Seconds), interval = Span(500, Millis))

  "AdvancedRepository" should {

    "search teas with filters" in {
      val advancedRepository = app.injector.instanceOf[AdvancedRepository]
      val result = advancedRepository.searchTeas(
        userId = Some(1L),
        name = Some("test"),
        page = 1,
        pageSize = 10
      )
      
      whenReady(result) { case (teas, totalCount) =>
        teas mustBe a[Seq[_]]
        totalCount mustBe a[Int]
        totalCount must be >= 0
      }
    }

    "get tea statistics" in {
      val advancedRepository = app.injector.instanceOf[AdvancedRepository]
      val result = advancedRepository.getTeaStatistics(1L)
      
      whenReady(result) { stats =>
        stats mustBe a[Map[_, _]]
        stats must contain key "totalCount"
        stats must contain key "inStockCount"
        stats must contain key "outOfStockCount"
        stats must contain key "avgPrice"
        stats must contain key "totalValue"
        stats must contain key "typeDistribution"
        stats must contain key "monthlyPurchases"
      }
    }

    "get low stock teas" in {
      val advancedRepository = app.injector.instanceOf[AdvancedRepository]
      val result = advancedRepository.getLowStockTeas(1L, 50)
      
      whenReady(result) { teas =>
        teas mustBe a[Seq[_]]
        teas.foreach { tea =>
          tea mustBe a[Tea]
        }
      }
    }

    "get expired teas" in {
      val advancedRepository = app.injector.instanceOf[AdvancedRepository]
      val result = advancedRepository.getExpiredTeas(1L, 180)
      
      whenReady(result) { teas =>
        teas mustBe a[Seq[_]]
        teas.foreach { tea =>
          tea mustBe a[Tea]
        }
      }
    }

    "batch update tea status" in {
      val advancedRepository = app.injector.instanceOf[AdvancedRepository]
      val result = advancedRepository.batchUpdateTeaStatus(Seq(1L, 2L), TeaStatusType.Archived)
      
      whenReady(result) { count =>
        count mustBe a[Int]
        count must be >= 0
      }
    }

    "batch delete teas" in {
      val advancedRepository = app.injector.instanceOf[AdvancedRepository]
      val result = advancedRepository.batchDeleteTeas(Seq(1L, 2L))
      
      whenReady(result) { count =>
        count mustBe a[Int]
        count must be >= 0
      }
    }

    "transfer tea ownership" in {
      val advancedRepository = app.injector.instanceOf[AdvancedRepository]
      val result = advancedRepository.transferTeaOwnership(1L, 1L, 2L)
      
      whenReady(result) { success =>
        success mustBe a[Boolean]
      }
    }

    "cleanup old data" in {
      val advancedRepository = app.injector.instanceOf[AdvancedRepository]
      val result = advancedRepository.cleanupOldData(365)
      
      whenReady(result) { count =>
        count mustBe a[Int]
        count must be >= 0
      }
    }

    "check data integrity" in {
      val advancedRepository = app.injector.instanceOf[AdvancedRepository]
      val result = advancedRepository.checkDataIntegrity()
      
      whenReady(result) { integrity =>
        integrity mustBe a[Map[_, _]]
        integrity must contain key "orphanedTeas"
        integrity must contain key "invalidPrices"
        integrity must contain key "invalidQuantities"
        integrity must contain key "isHealthy"
      }
    }

    "export user data" in {
      val advancedRepository = app.injector.instanceOf[AdvancedRepository]
      val result = advancedRepository.exportUserData(1L)
      
      whenReady(result) { data =>
        data mustBe a[Map[_, _]]
        data must contain key "user"
        data must contain key "teas"
        data must contain key "exportedAt"
        data must contain key "recordCount"
      }
    }

    "import user data" in {
      val advancedRepository = app.injector.instanceOf[AdvancedRepository]
      val data = Map("teas" -> Seq.empty[Tea])
      val result = advancedRepository.importUserData(1L, data)
      
      whenReady(result) { count =>
        count mustBe a[Int]
        count must be >= 0
      }
    }
  }
}

/**
 * ApiManagerのテスト
 */
class ApiManagerSpec extends PlaySpec with GuiceOneAppPerTest with ScalaFutures {

  implicit val defaultPatience = PatienceConfig(timeout = Span(5, Seconds), interval = Span(500, Millis))

  "ApiManager" should {

    "update usage stats" in {
      val apiManager = app.injector.instanceOf[ApiManager]
      
      noException should be thrownBy {
        apiManager.updateUsageStats("test-endpoint", 1L, true, 1000L)
      }
    }

    "check rate limit" in {
      val apiManager = app.injector.instanceOf[ApiManager]
      val result = apiManager.checkRateLimit(1L, "test-endpoint")
      
      whenReady(result) { allowed =>
        allowed mustBe a[Boolean]
      }
    }

    "generate api documentation" in {
      val apiManager = app.injector.instanceOf[ApiManager]
      val result = apiManager.generateApiDocumentation()
      
      result mustBe a[Map[_, _]]
      result must contain key "versions"
      result must contain key "endpoints"
      result must contain key "totalEndpoints"
      result must contain key "deprecatedEndpoints"
      result must contain key "generatedAt"
    }

    "get api usage stats" in {
      val apiManager = app.injector.instanceOf[ApiManager]
      val result = apiManager.getApiUsageStats()
      
      result mustBe a[Map[_, _]]
      result must contain key "totalRequests"
      result must contain key "successfulRequests"
      result must contain key "failedRequests"
      result must contain key "successRate"
      result must contain key "averageResponseTime"
      result must contain key "endpointStats"
      result must contain key "mostUsedEndpoints"
      result must contain key "lastUpdated"
    }

    "perform health check" in {
      val apiManager = app.injector.instanceOf[ApiManager]
      val result = apiManager.performHealthCheck()
      
      whenReady(result) { health =>
        health mustBe a[Map[_, _]]
        health must contain key "status"
        health must contain key "timestamp"
        health must contain key "apiVersions"
        health must contain key "registeredEndpoints"
        health must contain key "activeRateLimits"
        health must contain key "memoryUsage"
        health must contain key "uptime"
      }
    }

    "validate api version" in {
      val apiManager = app.injector.instanceOf[ApiManager]
      val result = apiManager.validateApiVersion("v1")
      
      whenReady(result) { valid =>
        valid mustBe a[Boolean]
      }
    }

    "check deprecation warning" in {
      val apiManager = app.injector.instanceOf[ApiManager]
      val result = apiManager.checkDeprecationWarning("v1.0")
      
      whenReady(result) { warning =>
        warning mustBe a[Option[_]]
      }
    }

    "generate api key" in {
      val apiManager = app.injector.instanceOf[ApiManager]
      val result = apiManager.generateApiKey(1L, Set("read", "write"))
      
      result mustBe a[String]
      result must startWith("ak_")
    }

    "validate api key" in {
      val apiManager = app.injector.instanceOf[ApiManager]
      val result = apiManager.validateApiKey("ak_123_1234567890_abcdefgh")
      
      whenReady(result) { userId =>
        userId mustBe a[Option[_]]
      }
    }

    "log api request" in {
      val apiManager = app.injector.instanceOf[ApiManager]
      
      noException should be thrownBy {
        apiManager.logApiRequest(
          endpoint = "test-endpoint",
          method = "GET",
          userId = Some(1L),
          ipAddress = "192.168.1.1",
          userAgent = "Mozilla/5.0",
          requestBody = None,
          responseStatus = 200,
          responseTime = 1000L
        )
      }
    }

    "get api metrics" in {
      val apiManager = app.injector.instanceOf[ApiManager]
      val result = apiManager.getApiMetrics()
      
      whenReady(result) { metrics =>
        metrics mustBe a[Map[_, _]]
        metrics must contain key "requestsLast24Hours"
        metrics must contain key "averageResponseTime"
        metrics must contain key "successRate"
        metrics must contain key "topEndpoints"
        metrics must contain key "errorRate"
      }
    }

    "cache api response" in {
      val apiManager = app.injector.instanceOf[ApiManager]
      
      noException should be thrownBy {
        apiManager.cacheApiResponse("test-key", play.api.libs.json.Json.obj("test" -> "value"))
      }
    }

    "get cached api response" in {
      val apiManager = app.injector.instanceOf[ApiManager]
      val result = apiManager.getCachedApiResponse("test-key")
      
      whenReady(result) { response =>
        response mustBe a[Option[_]]
      }
    }

    "invalidate api cache" in {
      val apiManager = app.injector.instanceOf[ApiManager]
      
      noException should be thrownBy {
        apiManager.invalidateApiCache("test-pattern")
      }
    }

    "check api security" in {
      val apiManager = app.injector.instanceOf[ApiManager]
      val result = apiManager.checkApiSecurity("test-endpoint", 1L, "192.168.1.1")
      
      whenReady(result) { secure =>
        secure mustBe a[Boolean]
      }
    }

    "block suspicious activity" in {
      val apiManager = app.injector.instanceOf[ApiManager]
      
      noException should be thrownBy {
        apiManager.blockSuspiciousActivity(1L, "192.168.1.1", "Suspicious behavior detected")
      }
    }
  }
}

/**
 * WorkflowManagerのテスト
 */
class WorkflowManagerSpec extends PlaySpec with GuiceOneAppPerTest with ScalaFutures {

  implicit val defaultPatience = PatienceConfig(timeout = Span(5, Seconds), interval = Span(500, Millis))

  "WorkflowManager" should {

    "register workflow" in {
      val workflowManager = app.injector.instanceOf[WorkflowManager]
      val workflow = WorkflowDefinition(
        id = "test-workflow",
        name = "Test Workflow",
        description = "A test workflow",
        version = "1.0",
        steps = List(
          WorkflowStep(
            id = "step1",
            name = "Step 1",
            stepType = "data_processing",
            config = Map.empty
          )
        )
      )
      
      val result = workflowManager.registerWorkflow(workflow)
      
      whenReady(result) { workflowId =>
        workflowId mustBe "test-workflow"
      }
    }

    "start workflow" in {
      val workflowManager = app.injector.instanceOf[WorkflowManager]
      
      // まずワークフローを登録
      val workflow = WorkflowDefinition(
        id = "test-workflow-2",
        name = "Test Workflow 2",
        description = "A test workflow",
        version = "1.0",
        steps = List(
          WorkflowStep(
            id = "step1",
            name = "Step 1",
            stepType = "data_processing",
            config = Map.empty
          )
        )
      )
      
      val registerResult = workflowManager.registerWorkflow(workflow)
      
      whenReady(registerResult) { _ =>
        val result = workflowManager.startWorkflow("test-workflow-2", Map("test" -> play.api.libs.json.Json.toJson("value")))
        
        whenReady(result) { executionId =>
          executionId mustBe a[String]
          executionId must not be empty
        }
      }
    }

    "get workflow execution" in {
      val workflowManager = app.injector.instanceOf[WorkflowManager]
      val result = workflowManager.getWorkflowExecution("non-existent-id")
      
      whenReady(result) { execution =>
        execution mustBe None
      }
    }

    "get workflow executions" in {
      val workflowManager = app.injector.instanceOf[WorkflowManager]
      val result = workflowManager.getWorkflowExecutions(limit = 10)
      
      whenReady(result) { executions =>
        executions mustBe a[List[_]]
        executions.foreach { execution =>
          execution mustBe a[WorkflowExecution]
        }
      }
    }

    "stop workflow execution" in {
      val workflowManager = app.injector.instanceOf[WorkflowManager]
      val result = workflowManager.stopWorkflowExecution("non-existent-id")
      
      whenReady(result) { stopped =>
        stopped mustBe false
      }
    }

    "pause workflow execution" in {
      val workflowManager = app.injector.instanceOf[WorkflowManager]
      val result = workflowManager.pauseWorkflowExecution("non-existent-id")
      
      whenReady(result) { paused =>
        paused mustBe false
      }
    }

    "resume workflow execution" in {
      val workflowManager = app.injector.instanceOf[WorkflowManager]
      val result = workflowManager.resumeWorkflowExecution("non-existent-id")
      
      whenReady(result) { resumed =>
        resumed mustBe false
      }
    }

    "get workflow statistics" in {
      val workflowManager = app.injector.instanceOf[WorkflowManager]
      val result = workflowManager.getWorkflowStatistics()
      
      whenReady(result) { stats =>
        stats mustBe a[Map[_, _]]
        stats must contain key "totalExecutions"
        stats must contain key "completedExecutions"
        stats must contain key "failedExecutions"
        stats must contain key "runningExecutions"
        stats must contain key "successRate"
        stats must contain key "averageExecutionTimeMinutes"
        stats must contain key "workflowDefinitions"
        stats must contain key "lastUpdated"
      }
    }

    "cleanup old executions" in {
      val workflowManager = app.injector.instanceOf[WorkflowManager]
      val result = workflowManager.cleanupOldExecutions(30)
      
      whenReady(result) { count =>
        count mustBe a[Int]
        count must be >= 0
      }
    }
  }
}

/**
 * SystemMonitorのテスト
 */
class SystemMonitorSpec extends PlaySpec with GuiceOneAppPerTest with ScalaFutures {

  implicit val defaultPatience = PatienceConfig(timeout = Span(5, Seconds), interval = Span(500, Millis))

  "SystemMonitor" should {

    "collect system metrics" in {
      val systemMonitor = app.injector.instanceOf[SystemMonitor]
      val result = systemMonitor.collectSystemMetrics()
      
      whenReady(result) { metrics =>
        metrics mustBe a[SystemMetrics]
        metrics.timestamp mustBe a[LocalDateTime]
        metrics.cpu mustBe a[CpuMetrics]
        metrics.memory mustBe a[MemoryMetrics]
        metrics.disk mustBe a[DiskMetrics]
        metrics.network mustBe a[NetworkMetrics]
        metrics.jvm mustBe a[JvmMetrics]
        metrics.application mustBe a[ApplicationMetrics]
      }
    }

    "record request" in {
      val systemMonitor = app.injector.instanceOf[SystemMonitor]
      
      noException should be thrownBy {
        systemMonitor.recordRequest(1000L)
      }
    }

    "record error" in {
      val systemMonitor = app.injector.instanceOf[SystemMonitor]
      
      noException should be thrownBy {
        systemMonitor.recordError()
      }
    }

    "record cache hit" in {
      val systemMonitor = app.injector.instanceOf[SystemMonitor]
      
      noException should be thrownBy {
        systemMonitor.recordCacheHit()
      }
    }

    "record cache miss" in {
      val systemMonitor = app.injector.instanceOf[SystemMonitor]
      
      noException should be thrownBy {
        systemMonitor.recordCacheMiss()
      }
    }

    "set active users" in {
      val systemMonitor = app.injector.instanceOf[SystemMonitor]
      
      noException should be thrownBy {
        systemMonitor.setActiveUsers(10)
      }
    }

    "perform health check" in {
      val systemMonitor = app.injector.instanceOf[SystemMonitor]
      val result = systemMonitor.performHealthCheck()
      
      whenReady(result) { checks =>
        checks mustBe a[List[_]]
        checks.foreach { check =>
          check mustBe a[HealthCheck]
          check.component mustBe a[String]
          check.status mustBe a[HealthStatus]
          check.message mustBe a[String]
          check.timestamp mustBe a[LocalDateTime]
          check.responseTime mustBe a[Long]
        }
      }
    }

    "add alert rule" in {
      val systemMonitor = app.injector.instanceOf[SystemMonitor]
      val rule = AlertRule(
        id = "test-rule",
        name = "Test Rule",
        condition = CpuUsageThreshold(80.0),
        severity = Warning
      )
      
      noException should be thrownBy {
        systemMonitor.addAlertRule(rule)
      }
    }

    "check alerts" in {
      val systemMonitor = app.injector.instanceOf[SystemMonitor]
      val metrics = SystemMetrics(
        timestamp = LocalDateTime.now(),
        cpu = CpuMetrics(85.0, 1.5, 10, 50),
        memory = MemoryMetrics(1000L, 500L, 500L, 50.0, 400L, 800L, 100L),
        disk = DiskMetrics(1000L, 300L, 700L, 30.0, 0L, 0L, 0L, 0L),
        network = NetworkMetrics(0L, 0L, 0L, 0L, 0, 0),
        jvm = JvmMetrics(0L, 0L, 0L, 50, 10, 100, 1000L, 1000L),
        application = ApplicationMetrics(100L, 5L, 1000.0, 10, 0.8, 5, 0)
      )
      
      val result = systemMonitor.checkAlerts(metrics)
      
      whenReady(result) { alerts =>
        alerts mustBe a[List[_]]
        alerts.foreach { alert =>
          alert mustBe a[Alert]
          alert.id mustBe a[String]
          alert.severity mustBe a[AlertSeverity]
          alert.title mustBe a[String]
          alert.message mustBe a[String]
          alert.timestamp mustBe a[LocalDateTime]
          alert.resolved mustBe a[Boolean]
        }
      }
    }

    "get active alerts" in {
      val systemMonitor = app.injector.instanceOf[SystemMonitor]
      val result = systemMonitor.getActiveAlerts()
      
      whenReady(result) { alerts =>
        alerts mustBe a[List[_]]
        alerts.foreach { alert =>
          alert mustBe a[Alert]
          alert.resolved mustBe false
        }
      }
    }

    "resolve alert" in {
      val systemMonitor = app.injector.instanceOf[SystemMonitor]
      val result = systemMonitor.resolveAlert("non-existent-alert")
      
      whenReady(result) { resolved =>
        resolved mustBe false
      }
    }

    "get metrics history" in {
      val systemMonitor = app.injector.instanceOf[SystemMonitor]
      val result = systemMonitor.getMetricsHistory(24)
      
      whenReady(result) { history =>
        history mustBe a[List[_]]
        history.foreach { metrics =>
          metrics mustBe a[SystemMetrics]
        }
      }
    }

    "get system statistics" in {
      val systemMonitor = app.injector.instanceOf[SystemMonitor]
      val result = systemMonitor.getSystemStatistics()
      
      whenReady(result) { stats =>
        stats mustBe a[Map[_, _]]
        stats must contain key "metrics"
        stats must contain key "healthChecks"
        stats must contain key "activeAlerts"
        stats must contain key "systemUptime"
        stats must contain key "lastUpdated"
      }
    }
  }
}
