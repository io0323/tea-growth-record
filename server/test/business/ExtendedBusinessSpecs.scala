package test.business

import org.scalatest.concurrent.ScalaFutures
import org.scalatest.time.{Millis, Seconds, Span}
import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneAppPerTest
import play.api.test.Helpers._
import play.api.test._
import business._
import services._
import models._
import scala.concurrent.ExecutionContext

/**
 * TeaBusinessLogicのテスト
 */
class TeaBusinessLogicSpec extends PlaySpec with GuiceOneAppPerTest with ScalaFutures {

  implicit val defaultPatience = PatienceConfig(timeout = Span(5, Seconds), interval = Span(500, Millis))

  "TeaBusinessLogic" should {

    "analyze inventory correctly" in {
      val teaBusinessLogic = app.injector.instanceOf[TeaBusinessLogic]
      val result = teaBusinessLogic.analyzeInventory(1L)
      
      whenReady(result) { inventory =>
        inventory mustBe a[InventoryStatus]
        inventory.totalItems mustBe a[Int]
        inventory.inStockItems mustBe a[Int]
        inventory.outOfStockItems mustBe a[Int]
        inventory.totalValue mustBe a[BigDecimal]
        inventory.averagePrice mustBe a[BigDecimal]
      }
    }

    "assess tea quality correctly" in {
      val teaBusinessLogic = app.injector.instanceOf[TeaBusinessLogic]
      val tea = models.Tea(
        name = "Test Tea",
        typeId = 1L,
        origin = "Japan",
        purchaseDate = java.time.LocalDateTime.now().minusDays(30),
        status = TeaStatusType.InStock,
        description = Some("High quality tea"),
        price = Some(3000),
        quantity = Some(100),
        unit = Some("g"),
        userId = Some(1L)
      )
      
      val result = teaBusinessLogic.assessTeaQuality(tea)
      
      result mustBe a[QualityAssessment]
      result.freshnessScore mustBe a[Int]
      result.storageScore mustBe a[Int]
      result.overallScore mustBe a[Int]
      result.recommendations mustBe a[List[_]]
    }

    "generate purchase recommendations" in {
      val teaBusinessLogic = app.injector.instanceOf[TeaBusinessLogic]
      val result = teaBusinessLogic.generatePurchaseRecommendations(1L)
      
      whenReady(result) { recommendations =>
        recommendations mustBe a[List[_]]
        recommendations.foreach { rec =>
          rec mustBe a[PurchaseRecommendation]
          rec.teaType mustBe a[String]
          rec.priority mustBe a[String]
          rec.reason mustBe a[String]
          rec.suggestedQuantity mustBe a[BigDecimal]
          rec.estimatedCost mustBe a[BigDecimal]
        }
      }
    }

    "predict tea consumption" in {
      val teaBusinessLogic = app.injector.instanceOf[TeaBusinessLogic]
      val result = teaBusinessLogic.predictTeaConsumption(1L)
      
      whenReady(result) { predictions =>
        predictions mustBe a[Map[_, _]]
        predictions.values.foreach { value =>
          value mustBe a[BigDecimal]
        }
      }
    }

    "optimize tea collection" in {
      val teaBusinessLogic = app.injector.instanceOf[TeaBusinessLogic]
      val result = teaBusinessLogic.optimizeTeaCollection(1L)
      
      whenReady(result) { suggestions =>
        suggestions mustBe a[List[_]]
        suggestions.foreach { suggestion =>
          suggestion mustBe a[String]
        }
      }
    }

    "get tea statistics" in {
      val teaBusinessLogic = app.injector.instanceOf[TeaBusinessLogic]
      val result = teaBusinessLogic.getTeaStatistics(1L)
      
      whenReady(result) { stats =>
        stats mustBe a[Map[_, _]]
        stats must contain key "totalTeas"
        stats must contain key "typeDistribution"
        stats must contain key "statusDistribution"
        stats must contain key "averagePrice"
        stats must contain key "totalValue"
      }
    }
  }
}

/**
 * DataAnalyticsのテスト
 */
class DataAnalyticsSpec extends PlaySpec with GuiceOneAppPerTest with ScalaFutures {

  implicit val defaultPatience = PatienceConfig(timeout = Span(5, Seconds), interval = Span(500, Millis))

  "DataAnalytics" should {

    "analyze purchase patterns" in {
      val dataAnalytics = app.injector.instanceOf[DataAnalytics]
      val result = dataAnalytics.analyzePurchasePatterns(1L)
      
      whenReady(result) { patterns =>
        patterns mustBe a[Map[_, _]]
        patterns must contain key "monthlyPurchases"
        patterns must contain key "priceDistribution"
        patterns must contain key "typePreferences"
        patterns must contain key "averageMonthlySpending"
        patterns must contain key "purchaseFrequency"
        patterns must contain key "seasonalPatterns"
      }
    }

    "analyze quality trends" in {
      val dataAnalytics = app.injector.instanceOf[DataAnalytics]
      val result = dataAnalytics.analyzeQualityTrends(1L)
      
      whenReady(result) { trends =>
        trends mustBe a[TrendAnalysis]
        trends.trend mustBe a[String]
        trends.changeRate mustBe a[BigDecimal]
        trends.confidence mustBe a[Double]
        trends.forecast mustBe a[List[_]]
      }
    }

    "analyze tea type correlations" in {
      val dataAnalytics = app.injector.instanceOf[DataAnalytics]
      val result = dataAnalytics.analyzeTeaTypeCorrelations(1L)
      
      whenReady(result) { correlations =>
        correlations mustBe a[List[_]]
        correlations.foreach { correlation =>
          correlation mustBe a[CorrelationAnalysis]
          correlation.variable1 mustBe a[String]
          correlation.variable2 mustBe a[String]
          correlation.correlation mustBe a[Double]
          correlation.significance mustBe a[Double]
          correlation.interpretation mustBe a[String]
        }
      }
    }

    "cluster teas" in {
      val dataAnalytics = app.injector.instanceOf[DataAnalytics]
      val result = dataAnalytics.clusterTeas(1L)
      
      whenReady(result) { clusters =>
        clusters mustBe a[ClusterAnalysis]
        clusters.clusters mustBe a[Map[_, _]]
        clusters.centroids mustBe a[Map[_, _]]
        clusters.silhouetteScore mustBe a[Double]
      }
    }

    "generate comprehensive report" in {
      val dataAnalytics = app.injector.instanceOf[DataAnalytics]
      val result = dataAnalytics.generateComprehensiveReport(1L)
      
      whenReady(result) { report =>
        report mustBe a[Map[_, _]]
        report must contain key "purchasePatterns"
        report must contain key "qualityTrends"
        report must contain key "correlations"
        report must contain key "clusters"
        report must contain key "generatedAt"
        report must contain key "summary"
      }
    }
  }
}

/**
 * SecurityServiceのテスト
 */
class SecurityServiceSpec extends PlaySpec with GuiceOneAppPerTest with ScalaFutures {

  implicit val defaultPatience = PatienceConfig(timeout = Span(5, Seconds), interval = Span(500, Millis))

  "SecurityService" should {

    "hash and verify passwords" in {
      val securityService = app.injector.instanceOf[SecurityService]
      val password = "TestPassword123!"
      
      val hashedPassword = securityService.hashPassword(password)
      hashedPassword mustBe a[String]
      hashedPassword must not be password
      
      val isValid = securityService.verifyPassword(password, hashedPassword)
      isValid mustBe true
      
      val isInvalid = securityService.verifyPassword("WrongPassword", hashedPassword)
      isInvalid mustBe false
    }

    "validate password policy" in {
      val securityService = app.injector.instanceOf[SecurityService]
      
      val validPassword = "ValidPassword123!"
      val errors = securityService.validatePasswordPolicy(validPassword)
      errors mustBe a[List[_]]
      
      val invalidPassword = "weak"
      val invalidErrors = securityService.validatePasswordPolicy(invalidPassword)
      invalidErrors mustBe a[List[_]]
      invalidErrors must not be empty
    }

    "create and validate sessions" in {
      val securityService = app.injector.instanceOf[SecurityService]
      val userId = 1L
      val ipAddress = "192.168.1.1"
      val userAgent = "Mozilla/5.0"
      
      val sessionId = securityService.createSession(userId, ipAddress, userAgent)
      sessionId mustBe a[String]
      sessionId must not be empty
      
      val validatedUserId = securityService.validateSession(sessionId, ipAddress, userAgent)
      validatedUserId mustBe Some(userId)
      
      val invalidSession = securityService.validateSession("invalid_session", ipAddress, userAgent)
      invalidSession mustBe None
    }

    "record authentication attempts" in {
      val securityService = app.injector.instanceOf[SecurityService]
      
      noException should be thrownBy {
        securityService.recordAuthenticationAttempt(
          email = "test@example.com",
          success = true,
          ipAddress = "192.168.1.1",
          userAgent = "Mozilla/5.0"
        )
      }
      
      noException should be thrownBy {
        securityService.recordAuthenticationAttempt(
          email = "test@example.com",
          success = false,
          ipAddress = "192.168.1.1",
          userAgent = "Mozilla/5.0",
          failureReason = Some("Invalid password")
        )
      }
    }

    "detect brute force attacks" in {
      val securityService = app.injector.instanceOf[SecurityService]
      val ipAddress = "192.168.1.1"
      
      // 複数の失敗した認証試行を記録
      for (i <- 1 to 6) {
        securityService.recordAuthenticationAttempt(
          email = s"test$i@example.com",
          success = false,
          ipAddress = ipAddress,
          userAgent = "Mozilla/5.0"
        )
      }
      
      val isBruteForce = securityService.detectBruteForceAttack(ipAddress)
      isBruteForce mustBe true
    }

    "detect anomalous access" in {
      val securityService = app.injector.instanceOf[SecurityService]
      val userId = 1L
      
      // 複数のセッションを作成
      for (i <- 1 to 3) {
        securityService.createSession(userId, s"192.168.1.$i", "Mozilla/5.0")
      }
      
      val isAnomalous = securityService.detectAnomalousAccess(userId, "192.168.1.4", "Mozilla/5.0")
      isAnomalous mustBe true
    }

    "encrypt and decrypt data" in {
      val securityService = app.injector.instanceOf[SecurityService]
      val data = "Sensitive data"
      val key = "MySecretKey12345" // 16文字のキー
      
      val encrypted = securityService.encryptData(data, key)
      encrypted mustBe a[String]
      encrypted must not be data
      
      val decrypted = securityService.decryptData(encrypted, key)
      decrypted mustBe data
    }

    "calculate security score" in {
      val securityService = app.injector.instanceOf[SecurityService]
      val result = securityService.calculateSecurityScore(1L)
      
      whenReady(result) { score =>
        score mustBe a[Int]
        score must be >= 0
        score must be <= 100
      }
    }

    "generate security report" in {
      val securityService = app.injector.instanceOf[SecurityService]
      val result = securityService.generateSecurityReport(1L)
      
      whenReady(result) { report =>
        report mustBe a[Map[_, _]]
        report must contain key "securityScore"
        report must contain key "activeSessions"
        report must contain key "totalSessions"
        report must contain key "recentLoginAttempts"
        report must contain key "failedLoginAttempts"
        report must contain key "passwordPolicy"
        report must contain key "recommendations"
      }
    }

    "monitor security events" in {
      val securityService = app.injector.instanceOf[SecurityService]
      val events = securityService.monitorSecurityEvents()
      
      events mustBe a[List[_]]
      events.foreach { event =>
        event mustBe a[SecurityEvent]
        event.eventType mustBe a[String]
        event.severity mustBe a[String]
        event.timestamp mustBe a[LocalDateTime]
        event.details mustBe a[Map[_, _]]
      }
    }
  }
}

/**
 * NotificationServiceのテスト
 */
class NotificationServiceSpec extends PlaySpec with GuiceOneAppPerTest with ScalaFutures {

  implicit val defaultPatience = PatienceConfig(timeout = Span(5, Seconds), interval = Span(500, Millis))

  "NotificationService" should {

    "send notification" in {
      val notificationService = app.injector.instanceOf[NotificationService]
      val result = notificationService.sendNotification(
        userId = 1L,
        notificationType = LowStockAlert,
        title = "Test Notification",
        message = "This is a test notification",
        priority = Medium,
        channels = List(InApp)
      )
      
      whenReady(result) { notificationId =>
        notificationId mustBe a[String]
        notificationId must not be empty
      }
    }

    "send template notification" in {
      val notificationService = app.injector.instanceOf[NotificationService]
      val variables = Map(
        "teaName" -> "Test Tea",
        "quantity" -> "50",
        "unit" -> "g"
      )
      
      val result = notificationService.sendTemplateNotification(
        userId = 1L,
        notificationType = LowStockAlert,
        variables = variables
      )
      
      whenReady(result) { notificationId =>
        notificationId mustBe a[String]
        notificationId must not be empty
      }
    }

    "check low stock alerts" in {
      val notificationService = app.injector.instanceOf[NotificationService]
      val result = notificationService.checkLowStockAlerts(1L)
      
      whenReady(result) { notificationIds =>
        notificationIds mustBe a[List[_]]
        notificationIds.foreach { id =>
          id mustBe a[String]
        }
      }
    }

    "check expiration warnings" in {
      val notificationService = app.injector.instanceOf[NotificationService]
      val result = notificationService.checkExpirationWarnings(1L)
      
      whenReady(result) { notificationIds =>
        notificationIds mustBe a[List[_]]
        notificationIds.foreach { id =>
          id mustBe a[String]
        }
      }
    }

    "send purchase recommendations" in {
      val notificationService = app.injector.instanceOf[NotificationService]
      val result = notificationService.sendPurchaseRecommendations(1L)
      
      whenReady(result) { notificationIds =>
        notificationIds mustBe a[List[_]]
        notificationIds.foreach { id =>
          id mustBe a[String]
        }
      }
    }

    "get user notifications" in {
      val notificationService = app.injector.instanceOf[NotificationService]
      val result = notificationService.getUserNotifications(1L, 10)
      
      whenReady(result) { notifications =>
        notifications mustBe a[List[_]]
        notifications.foreach { notification =>
          notification mustBe a[Notification]
          notification.id mustBe a[String]
          notification.userId mustBe 1L
          notification.title mustBe a[String]
          notification.message mustBe a[String]
          notification.createdAt mustBe a[LocalDateTime]
        }
      }
    }

    "mark notification as read" in {
      val notificationService = app.injector.instanceOf[NotificationService]
      
      // まず通知を送信
      val sendResult = notificationService.sendNotification(
        userId = 1L,
        notificationType = LowStockAlert,
        title = "Test Notification",
        message = "This is a test notification"
      )
      
      whenReady(sendResult) { notificationId =>
        val markResult = notificationService.markAsRead(notificationId, 1L)
        
        whenReady(markResult) { marked =>
          marked mustBe true
        }
      }
    }

    "get unread count" in {
      val notificationService = app.injector.instanceOf[NotificationService]
      val result = notificationService.getUnreadCount(1L)
      
      whenReady(result) { count =>
        count mustBe a[Int]
        count must be >= 0
      }
    }

    "delete notification" in {
      val notificationService = app.injector.instanceOf[NotificationService]
      
      // まず通知を送信
      val sendResult = notificationService.sendNotification(
        userId = 1L,
        notificationType = LowStockAlert,
        title = "Test Notification",
        message = "This is a test notification"
      )
      
      whenReady(sendResult) { notificationId =>
        val deleteResult = notificationService.deleteNotification(notificationId, 1L)
        
        whenReady(deleteResult) { deleted =>
          deleted mustBe true
        }
      }
    }

    "cleanup old notifications" in {
      val notificationService = app.injector.instanceOf[NotificationService]
      val result = notificationService.cleanupOldNotifications(30)
      
      whenReady(result) { deletedCount =>
        deletedCount mustBe a[Int]
        deletedCount must be >= 0
      }
    }

    "get notification stats" in {
      val notificationService = app.injector.instanceOf[NotificationService]
      val result = notificationService.getNotificationStats(1L)
      
      whenReady(result) { stats =>
        stats mustBe a[Map[_, _]]
        stats must contain key "totalNotifications"
        stats must contain key "unreadCount"
        stats must contain key "readCount"
        stats must contain key "notificationsByType"
        stats must contain key "notificationsByPriority"
        stats must contain key "lastNotification"
        stats must contain key "averageNotificationsPerDay"
      }
    }

    "update notification settings" in {
      val notificationService = app.injector.instanceOf[NotificationService]
      val result = notificationService.updateNotificationSettings(
        userId = 1L,
        notificationType = LowStockAlert,
        enabled = true,
        channels = List(InApp, Email)
      )
      
      whenReady(result) { updated =>
        updated mustBe true
      }
    }
  }
}
