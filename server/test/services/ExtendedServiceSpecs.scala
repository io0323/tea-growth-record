package test.services

import org.scalatest.concurrent.ScalaFutures
import org.scalatest.time.{Millis, Seconds, Span}
import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneAppPerTest
import play.api.test.Helpers._
import play.api.test._
import services._
import models._
import utils.ValidationUtils
import scala.concurrent.ExecutionContext

/**
 * ValidationUtilsのテスト
 */
class ValidationUtilsSpec extends PlaySpec with GuiceOneAppPerTest with ScalaFutures {

  implicit val defaultPatience = PatienceConfig(timeout = Span(5, Seconds), interval = Span(500, Millis))

  "ValidationUtils" should {

    "validate email correctly" in {
      ValidationUtils.validateEmail("test@example.com") mustBe None
      ValidationUtils.validateEmail("invalid-email") mustBe Some("有効なメールアドレスを入力してください")
      ValidationUtils.validateEmail("") mustBe Some("有効なメールアドレスを入力してください")
    }

    "validate password strength" in {
      ValidationUtils.validatePassword("Weak123") mustBe Some("パスワードには特殊文字を含める必要があります")
      ValidationUtils.validatePassword("Strong123!") mustBe None
      ValidationUtils.validatePassword("weak") mustBe Some("パスワードは8文字以上である必要があります, パスワードには大文字を含める必要があります, パスワードには数字を含める必要があります, パスワードには特殊文字を含める必要があります")
    }

    "validate tea name" in {
      ValidationUtils.validateTeaName("") mustBe Some("お茶の名前を入力してください")
      ValidationUtils.validateTeaName("Valid Tea Name") mustBe None
      ValidationUtils.validateTeaName("A" * 101) mustBe Some("お茶の名前は100文字以内で入力してください")
    }

    "validate price" in {
      ValidationUtils.validatePrice(-1) mustBe Some("価格は0以上である必要があります")
      ValidationUtils.validatePrice(0) mustBe None
      ValidationUtils.validatePrice(1000001) mustBe Some("価格は1,000,000円以下である必要があります")
    }

    "validate quantity" in {
      ValidationUtils.validateQuantity(-1) mustBe Some("数量は0以上である必要があります")
      ValidationUtils.validateQuantity(0) mustBe None
      ValidationUtils.validateQuantity(10001) mustBe Some("数量は10,000以下である必要があります")
    }

    "validate date" in {
      ValidationUtils.validateDate("2023-01-01") mustBe None
      ValidationUtils.validateDate("invalid-date") mustBe Some("有効な日付を入力してください (yyyy-MM-dd)")
      ValidationUtils.validateDate("2030-01-01") mustBe Some("購入日は今日以前の日付である必要があります")
    }

    "validate tea data comprehensively" in {
      val result = ValidationUtils.validateTeaData(
        name = "Test Tea",
        price = 1000,
        quantity = 100,
        purchaseDate = "2023-01-01"
      )
      result.isValid mustBe true
      result.errors mustBe empty
    }

    "validate user data comprehensively" in {
      val result = ValidationUtils.validateUserData(
        email = "test@example.com",
        password = "Strong123!",
        name = Some("Test User")
      )
      result.isValid mustBe true
      result.errors mustBe empty
    }
  }
}

/**
 * TeaServiceのテスト
 */
class TeaServiceSpec extends PlaySpec with GuiceOneAppPerTest with ScalaFutures {

  implicit val defaultPatience = PatienceConfig(timeout = Span(5, Seconds), interval = Span(500, Millis))

  "TeaService" should {

    "find teas by user id" in {
      val teaService = app.injector.instanceOf[TeaService]
      val result = teaService.findByUserId(1L)
      
      whenReady(result) { teas =>
        teas mustBe a[Seq[_]]
      }
    }

    "find tea by id" in {
      val teaService = app.injector.instanceOf[TeaService]
      val result = teaService.findById(1L)
      
      whenReady(result) { tea =>
        tea mustBe a[Option[_]]
      }
    }

    "create tea" in {
      val teaService = app.injector.instanceOf[TeaService]
      val tea = models.Tea(
        name = "Test Tea",
        typeId = 1L,
        origin = "Japan",
        purchaseDate = java.time.LocalDateTime.now(),
        status = TeaStatusType.InStock,
        description = Some("Test description"),
        price = Some(1000),
        quantity = Some(100),
        unit = Some("g"),
        userId = Some(1L)
      )
      
      val result = teaService.create(tea)
      
      whenReady(result) { createdTea =>
        createdTea mustBe a[Option[_]]
      }
    }

    "update tea" in {
      val teaService = app.injector.instanceOf[TeaService]
      val tea = models.Tea(
        id = Some(1L),
        name = "Updated Tea",
        typeId = 1L,
        origin = "Japan",
        purchaseDate = java.time.LocalDateTime.now(),
        status = TeaStatusType.InStock,
        description = Some("Updated description"),
        price = Some(1500),
        quantity = Some(150),
        unit = Some("g"),
        userId = Some(1L)
      )
      
      val result = teaService.update(tea)
      
      whenReady(result) { updatedTea =>
        updatedTea mustBe a[Option[_]]
      }
    }

    "delete tea" in {
      val teaService = app.injector.instanceOf[TeaService]
      val result = teaService.delete(1L)
      
      whenReady(result) { deleted =>
        deleted mustBe a[Boolean]
      }
    }

    "update tea status" in {
      val teaService = app.injector.instanceOf[TeaService]
      val result = teaService.updateStatus(1L, TeaStatusType.OutOfStock)
      
      whenReady(result) { updated =>
        updated mustBe a[Boolean]
      }
    }

    "update tea quantity" in {
      val teaService = app.injector.instanceOf[TeaService]
      val result = teaService.updateQuantity(1L, 200)
      
      whenReady(result) { updated =>
        updated mustBe a[Boolean]
      }
    }
  }
}

/**
 * UserServiceのテスト
 */
class UserServiceSpec extends PlaySpec with GuiceOneAppPerTest with ScalaFutures {

  implicit val defaultPatience = PatienceConfig(timeout = Span(5, Seconds), interval = Span(500, Millis))

  "UserService" should {

    "find user by id" in {
      val userService = app.injector.instanceOf[UserService]
      val result = userService.findById(1L)
      
      whenReady(result) { user =>
        user mustBe a[Option[_]]
      }
    }

    "find user by email" in {
      val userService = app.injector.instanceOf[UserService]
      val result = userService.findByEmail("test@example.com")
      
      whenReady(result) { user =>
        user mustBe a[Option[_]]
      }
    }

    "authenticate user" in {
      val userService = app.injector.instanceOf[UserService]
      val result = userService.authenticate("test@example.com", "password")
      
      whenReady(result) { user =>
        user mustBe a[Option[_]]
      }
    }

    "create user" in {
      val userService = app.injector.instanceOf[UserService]
      val result = userService.create("newuser@example.com", "password", Some("New User"))
      
      whenReady(result) { user =>
        user mustBe a[Option[_]]
      }
    }

    "update user" in {
      val userService = app.injector.instanceOf[UserService]
      val user = models.User(
        id = Some(1L),
        email = "updated@example.com",
        password = "password",
        name = Some("Updated User")
      )
      
      val result = userService.update(user)
      
      whenReady(result) { updatedUser =>
        updatedUser mustBe a[Option[_]]
      }
    }

    "update password" in {
      val userService = app.injector.instanceOf[UserService]
      val result = userService.updatePassword(1L, "newpassword")
      
      whenReady(result) { updated =>
        updated mustBe a[Boolean]
      }
    }
  }
}

/**
 * PlantServiceのテスト
 */
class PlantServiceSpec extends PlaySpec with GuiceOneAppPerTest with ScalaFutures {

  implicit val defaultPatience = PatienceConfig(timeout = Span(5, Seconds), interval = Span(500, Millis))

  "PlantService" should {

    "list plants" in {
      val plantService = app.injector.instanceOf[PlantService]
      val result = plantService.list()
      
      whenReady(result) { plants =>
        plants mustBe a[Seq[_]]
      }
    }

    "get plant by id" in {
      val plantService = app.injector.instanceOf[PlantService]
      val result = plantService.get(1L)
      
      whenReady(result) { plant =>
        plant mustBe a[Option[_]]
      }
    }

    "create plant" in {
      val plantService = app.injector.instanceOf[PlantService]
      val plant = models.Plant(
        name = "Test Plant",
        species = "Test Species",
        userId = Some(1L)
      )
      
      val result = plantService.create(plant)
      
      whenReady(result) { createdPlant =>
        createdPlant mustBe a[Plant]
      }
    }

    "update plant" in {
      val plantService = app.injector.instanceOf[PlantService]
      val plant = models.Plant(
        name = "Updated Plant",
        species = "Updated Species",
        userId = Some(1L)
      )
      
      val result = plantService.update(1L, plant)
      
      whenReady(result) { updatedPlant =>
        updatedPlant mustBe a[Option[_]]
      }
    }

    "delete plant" in {
      val plantService = app.injector.instanceOf[PlantService]
      val result = plantService.delete(1L)
      
      whenReady(result) { deleted =>
        deleted mustBe a[Boolean]
      }
    }
  }
}

/**
 * LoggingServiceのテスト
 */
class LoggingServiceSpec extends PlaySpec with GuiceOneAppPerTest with ScalaFutures {

  implicit val defaultPatience = PatienceConfig(timeout = Span(5, Seconds), interval = Span(500, Millis))

  "LoggingService" should {

    "log user action" in {
      val loggingService = app.injector.instanceOf[LoggingService]
      
      // ログが例外を投げないことを確認
      noException should be thrownBy {
        loggingService.logUserAction("test_action", 1L, Map("detail" -> "test"))
      }
    }

    "log tea operation" in {
      val loggingService = app.injector.instanceOf[LoggingService]
      
      noException should be thrownBy {
        loggingService.logTeaOperation("test_operation", 1L, 1L, Map("detail" -> "test"))
      }
    }

    "log auth event" in {
      val loggingService = app.injector.instanceOf[LoggingService]
      
      noException should be thrownBy {
        loggingService.logAuthEvent("test_event", "test@example.com", true, Map("detail" -> "test"))
      }
    }

    "log error" in {
      val loggingService = app.injector.instanceOf[LoggingService]
      
      noException should be thrownBy {
        loggingService.logError("test error", new RuntimeException("test"), Map("detail" -> "test"))
      }
    }

    "log performance" in {
      val loggingService = app.injector.instanceOf[LoggingService]
      
      noException should be thrownBy {
        loggingService.logPerformance("test_operation", 1000L, Map("detail" -> "test"))
      }
    }

    "log security event" in {
      val loggingService = app.injector.instanceOf[LoggingService]
      
      noException should be thrownBy {
        loggingService.logSecurityEvent("test_event", "high", Map("detail" -> "test"))
      }
    }

    "log database operation" in {
      val loggingService = app.injector.instanceOf[LoggingService]
      
      noException should be thrownBy {
        loggingService.logDatabaseOperation("test_operation", "test_table", 100L, true, Map("detail" -> "test"))
      }
    }

    "get log stats" in {
      val loggingService = app.injector.instanceOf[LoggingService]
      val stats = loggingService.getLogStats()
      
      stats mustBe a[Map[_, _]]
      stats must contain key "totalLogs"
      stats must contain key "errorCount"
      stats must contain key "warningCount"
      stats must contain key "infoCount"
      stats must contain key "lastLogTime"
    }
  }
}

/**
 * MetricsServiceのテスト
 */
class MetricsServiceSpec extends PlaySpec with GuiceOneAppPerTest with ScalaFutures {

  implicit val defaultPatience = PatienceConfig(timeout = Span(5, Seconds), interval = Span(500, Millis))

  "MetricsService" should {

    "increment counters" in {
      val metricsService = app.injector.instanceOf[MetricsService]
      
      noException should be thrownBy {
        metricsService.incrementRequestCount()
        metricsService.incrementErrorCount()
        metricsService.incrementTeaOperationCount()
        metricsService.incrementUserLoginCount()
        metricsService.incrementCacheHitCount()
        metricsService.incrementCacheMissCount()
      }
    }

    "record times" in {
      val metricsService = app.injector.instanceOf[MetricsService]
      
      noException should be thrownBy {
        metricsService.recordResponseTime(1000L)
        metricsService.recordDatabaseTime(500L)
        metricsService.recordCacheTime(100L)
      }
    }

    "set gauges" in {
      val metricsService = app.injector.instanceOf[MetricsService]
      
      noException should be thrownBy {
        metricsService.setActiveUsers(10L)
        metricsService.setActiveSessions(5L)
        metricsService.setMemoryUsage(1024L * 1024L)
      }
    }

    "record performance metrics" in {
      val metricsService = app.injector.instanceOf[MetricsService]
      
      noException should be thrownBy {
        metricsService.recordPerformanceMetrics("test_operation", 1000L, true)
        metricsService.recordPerformanceMetrics("test_operation", 1000L, false)
      }
    }

    "record database metrics" in {
      val metricsService = app.injector.instanceOf[MetricsService]
      
      noException should be thrownBy {
        metricsService.recordDatabaseMetrics("test_operation", 500L, true)
        metricsService.recordDatabaseMetrics("test_operation", 500L, false)
      }
    }

    "record cache metrics" in {
      val metricsService = app.injector.instanceOf[MetricsService]
      
      noException should be thrownBy {
        metricsService.recordCacheMetrics("test_operation", 100L, true)
        metricsService.recordCacheMetrics("test_operation", 100L, false)
      }
    }

    "get current metrics" in {
      val metricsService = app.injector.instanceOf[MetricsService]
      val metrics = metricsService.getCurrentMetrics()
      
      metrics mustBe a[MetricsData]
      metrics.timestamp mustBe a[String]
      metrics.counters mustBe a[Map[_, _]]
      metrics.timers mustBe a[Map[_, _]]
      metrics.gauges mustBe a[Map[_, _]]
      metrics.rates mustBe a[Map[_, _]]
    }

    "get metrics as JSON" in {
      val metricsService = app.injector.instanceOf[MetricsService]
      val json = metricsService.getMetricsAsJson()
      
      json mustBe a[play.api.libs.json.JsValue]
    }

    "get health metrics" in {
      val metricsService = app.injector.instanceOf[MetricsService]
      val health = metricsService.getHealthMetrics()
      
      health mustBe a[Map[_, _]]
      health must contain key "status"
      health must contain key "timestamp"
      health must contain key "error_rate"
      health must contain key "cache_hit_rate"
      health must contain key "active_users"
      health must contain key "memory_usage_mb"
    }

    "check alerts" in {
      val metricsService = app.injector.instanceOf[MetricsService]
      val alerts = metricsService.checkAlerts()
      
      alerts mustBe a[List[_]]
    }

    "reset metrics" in {
      val metricsService = app.injector.instanceOf[MetricsService]
      
      noException should be thrownBy {
        metricsService.resetMetrics()
      }
    }

    "get metrics history" in {
      val metricsService = app.injector.instanceOf[MetricsService]
      val history = metricsService.getMetricsHistory()
      
      history mustBe a[List[_]]
    }
  }
}
