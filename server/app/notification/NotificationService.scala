package notification

import models._
import services._
import scala.concurrent.{ExecutionContext, Future}
import java.time.{LocalDateTime, Duration}
import javax.inject.{Inject, Singleton}
import play.api.libs.json.{Json, JsValue}

/**
 * 高度な通知システム
 */
@Singleton
class NotificationService @Inject()(
  teaService: TeaService,
  userService: UserService,
  loggingService: LoggingService
)(implicit ec: ExecutionContext) {

  /**
   * 通知タイプ
   */
  sealed trait NotificationType
  case object LowStockAlert extends NotificationType
  case object ExpirationWarning extends NotificationType
  case object PurchaseRecommendation extends NotificationType
  case object QualityAlert extends NotificationType
  case object SecurityAlert extends NotificationType
  case object SystemMaintenance extends NotificationType

  /**
   * 通知優先度
   */
  sealed trait NotificationPriority
  case object Low extends NotificationPriority
  case object Medium extends NotificationPriority
  case object High extends NotificationPriority
  case object Critical extends NotificationPriority

  /**
   * 通知チャンネル
   */
  sealed trait NotificationChannel
  case object Email extends NotificationChannel
  case object InApp extends NotificationChannel
  case object Push extends NotificationChannel
  case object SMS extends NotificationChannel

  /**
   * 通知
   */
  case class Notification(
    id: String,
    userId: Long,
    notificationType: NotificationType,
    priority: NotificationPriority,
    title: String,
    message: String,
    channels: List[NotificationChannel],
    createdAt: LocalDateTime,
    scheduledFor: Option[LocalDateTime],
    sentAt: Option[LocalDateTime],
    readAt: Option[LocalDateTime],
    metadata: Map[String, String]
  )

  /**
   * 通知テンプレート
   */
  case class NotificationTemplate(
    notificationType: NotificationType,
    priority: NotificationPriority,
    titleTemplate: String,
    messageTemplate: String,
    channels: List[NotificationChannel],
    conditions: Map[String, String]
  )

  // メモリ内の通知管理（本番環境ではデータベースを使用）
  private val notifications = scala.collection.mutable.ListBuffer[Notification]()
  private val notificationTemplates = scala.collection.mutable.Map[NotificationType, NotificationTemplate]()

  /**
   * 通知テンプレートの初期化
   */
  initializeTemplates()

  private def initializeTemplates(): Unit = {
    notificationTemplates(LowStockAlert) = NotificationTemplate(
      notificationType = LowStockAlert,
      priority = Medium,
      titleTemplate = "在庫不足のお知らせ",
      messageTemplate = "お茶「{teaName}」の在庫が少なくなっています。現在の在庫: {quantity}{unit}",
      channels = List(InApp, Email),
      conditions = Map("quantity" -> "<50")
    )

    notificationTemplates(ExpirationWarning) = NotificationTemplate(
      notificationType = ExpirationWarning,
      priority = High,
      titleTemplate = "お茶の期限警告",
      messageTemplate = "お茶「{teaName}」は購入から{days}日経過しています。早めの消費をお勧めします。",
      channels = List(InApp, Email, Push),
      conditions = Map("days" -> ">180")
    )

    notificationTemplates(PurchaseRecommendation) = NotificationTemplate(
      notificationType = PurchaseRecommendation,
      priority = Low,
      titleTemplate = "お茶の購入推奨",
      messageTemplate = "お茶「{teaName}」の補充をお勧めします。推奨数量: {recommendedQuantity}{unit}",
      channels = List(InApp),
      conditions = Map("status" -> "OutOfStock")
    )

    notificationTemplates(QualityAlert) = NotificationTemplate(
      notificationType = QualityAlert,
      priority = Medium,
      titleTemplate = "お茶の品質アラート",
      messageTemplate = "お茶「{teaName}」の品質が低下している可能性があります。保存方法を確認してください。",
      channels = List(InApp, Email),
      conditions = Map("qualityScore" -> "<60")
    )

    notificationTemplates(SecurityAlert) = NotificationTemplate(
      notificationType = SecurityAlert,
      priority = Critical,
      titleTemplate = "セキュリティアラート",
      messageTemplate = "アカウントに異常なアクセスが検出されました。セキュリティ設定を確認してください。",
      channels = List(Email, SMS),
      conditions = Map("eventType" -> "security")
    )

    notificationTemplates(SystemMaintenance) = NotificationTemplate(
      notificationType = SystemMaintenance,
      priority = Medium,
      titleTemplate = "システムメンテナンスのお知らせ",
      messageTemplate = "システムメンテナンスを実施します。日時: {maintenanceDate}",
      channels = List(InApp, Email),
      conditions = Map("maintenance" -> "scheduled")
    )
  }

  /**
   * 通知の送信
   */
  def sendNotification(
    userId: Long,
    notificationType: NotificationType,
    title: String,
    message: String,
    priority: NotificationPriority = Medium,
    channels: List[NotificationChannel] = List(InApp),
    metadata: Map[String, String] = Map.empty
  ): Future[String] = {
    val notificationId = generateNotificationId()
    val now = LocalDateTime.now()
    
    val notification = Notification(
      id = notificationId,
      userId = userId,
      notificationType = notificationType,
      priority = priority,
      title = title,
      message = message,
      channels = channels,
      createdAt = now,
      scheduledFor = None,
      sentAt = Some(now),
      readAt = None,
      metadata = metadata
    )
    
    notifications += notification
    
    // 実際の送信処理
    sendToChannels(notification)
    
    loggingService.logUserAction(
      action = "notification_sent",
      userId = userId,
      details = Map(
        "notificationId" -> notificationId,
        "type" -> notificationType.toString,
        "priority" -> priority.toString
      )
    )
    
    Future.successful(notificationId)
  }

  /**
   * テンプレートベースの通知送信
   */
  def sendTemplateNotification(
    userId: Long,
    notificationType: NotificationType,
    variables: Map[String, String]
  ): Future[String] = {
    notificationTemplates.get(notificationType) match {
      case Some(template) =>
        val title = replaceVariables(template.titleTemplate, variables)
        val message = replaceVariables(template.messageTemplate, variables)
        
        sendNotification(
          userId = userId,
          notificationType = notificationType,
          title = title,
          message = message,
          priority = template.priority,
          channels = template.channels,
          metadata = variables
        )
      case None =>
        Future.failed(new IllegalArgumentException(s"Template not found for ${notificationType}"))
    }
  }

  /**
   * 変数の置換
   */
  private def replaceVariables(template: String, variables: Map[String, String]): String = {
    variables.foldLeft(template) { case (text, (key, value)) =>
      text.replace(s"{$key}", value)
    }
  }

  /**
   * チャンネル別の送信処理
   */
  private def sendToChannels(notification: Notification): Unit = {
    notification.channels.foreach {
      case Email => sendEmail(notification)
      case InApp => sendInApp(notification)
      case Push => sendPush(notification)
      case SMS => sendSMS(notification)
    }
  }

  /**
   * メール送信
   */
  private def sendEmail(notification: Notification): Unit = {
    // 実際のメール送信処理
    loggingService.logUserAction(
      action = "email_notification_sent",
      userId = notification.userId,
      details = Map(
        "notificationId" -> notification.id,
        "title" -> notification.title
      )
    )
  }

  /**
   * アプリ内通知送信
   */
  private def sendInApp(notification: Notification): Unit = {
    // アプリ内通知の処理
    loggingService.logUserAction(
      action = "inapp_notification_sent",
      userId = notification.userId,
      details = Map(
        "notificationId" -> notification.id,
        "title" -> notification.title
      )
    )
  }

  /**
   * プッシュ通知送信
   */
  private def sendPush(notification: Notification): Unit = {
    // プッシュ通知の処理
    loggingService.logUserAction(
      action = "push_notification_sent",
      userId = notification.userId,
      details = Map(
        "notificationId" -> notification.id,
        "title" -> notification.title
      )
    )
  }

  /**
   * SMS送信
   */
  private def sendSMS(notification: Notification): Unit = {
    // SMS送信の処理
    loggingService.logUserAction(
      action = "sms_notification_sent",
      userId = notification.userId,
      details = Map(
        "notificationId" -> notification.id,
        "title" -> notification.title
      )
    )
  }

  /**
   * 通知IDの生成
   */
  private def generateNotificationId(): String = {
    val timestamp = System.currentTimeMillis()
    val random = scala.util.Random.nextInt(1000)
    s"notif_${timestamp}_${random}"
  }

  /**
   * お茶の在庫アラート
   */
  def checkLowStockAlerts(userId: Long): Future[List[String]] = {
    teaService.findByUserId(userId).map { teas =>
      val lowStockTeas = teas.filter { tea =>
        tea.status == TeaStatusType.InStock && 
        tea.quantity.exists(_ < 50)
      }
      
      val notificationIds = scala.collection.mutable.ListBuffer[String]()
      
      lowStockTeas.foreach { tea =>
        val variables = Map(
          "teaName" -> tea.name,
          "quantity" -> tea.quantity.getOrElse(0).toString,
          "unit" -> tea.unit.map(_.toString).getOrElse("g")
        )
        
        // 非同期で通知を送信
        sendTemplateNotification(userId, LowStockAlert, variables).foreach { id =>
          notificationIds += id
        }
      }
      
      notificationIds.toList
    }
  }

  /**
   * お茶の期限警告
   */
  def checkExpirationWarnings(userId: Long): Future[List[String]] = {
    teaService.findByUserId(userId).map { teas =>
      val expiredTeas = teas.filter { tea =>
        val daysSincePurchase = java.time.Duration.between(tea.purchaseDate, LocalDateTime.now()).toDays
        daysSincePurchase > 180
      }
      
      val notificationIds = scala.collection.mutable.ListBuffer[String]()
      
      expiredTeas.foreach { tea =>
        val daysSincePurchase = java.time.Duration.between(tea.purchaseDate, LocalDateTime.now()).toDays
        val variables = Map(
          "teaName" -> tea.name,
          "days" -> daysSincePurchase.toString
        )
        
        sendTemplateNotification(userId, ExpirationWarning, variables).foreach { id =>
          notificationIds += id
        }
      }
      
      notificationIds.toList
    }
  }

  /**
   * 購入推奨通知
   */
  def sendPurchaseRecommendations(userId: Long): Future[List[String]] = {
    teaService.findByUserId(userId).map { teas =>
      val outOfStockTeas = teas.filter(_.status == TeaStatusType.OutOfStock)
      
      val notificationIds = scala.collection.mutable.ListBuffer[String]()
      
      outOfStockTeas.foreach { tea =>
        val variables = Map(
          "teaName" -> tea.name,
          "recommendedQuantity" -> "100",
          "unit" -> tea.unit.map(_.toString).getOrElse("g")
        )
        
        sendTemplateNotification(userId, PurchaseRecommendation, variables).foreach { id =>
          notificationIds += id
        }
      }
      
      notificationIds.toList
    }
  }

  /**
   * ユーザーの通知一覧取得
   */
  def getUserNotifications(userId: Long, limit: Int = 50): Future[List[Notification]] = {
    Future.successful(
      notifications
        .filter(_.userId == userId)
        .sortBy(_.createdAt)(Ordering[LocalDateTime].reverse)
        .take(limit)
        .toList
    )
  }

  /**
   * 通知の既読マーク
   */
  def markAsRead(notificationId: String, userId: Long): Future[Boolean] = {
    notifications.find(n => n.id == notificationId && n.userId == userId) match {
      case Some(notification) =>
        val index = notifications.indexOf(notification)
        val updatedNotification = notification.copy(readAt = Some(LocalDateTime.now()))
        notifications(index) = updatedNotification
        Future.successful(true)
      case None =>
        Future.successful(false)
    }
  }

  /**
   * 未読通知数取得
   */
  def getUnreadCount(userId: Long): Future[Int] = {
    Future.successful(
      notifications.count(n => n.userId == userId && n.readAt.isEmpty)
    )
  }

  /**
   * 通知の削除
   */
  def deleteNotification(notificationId: String, userId: Long): Future[Boolean] = {
    val index = notifications.indexWhere(n => n.id == notificationId && n.userId == userId)
    if (index >= 0) {
      notifications.remove(index)
      Future.successful(true)
    } else {
      Future.successful(false)
    }
  }

  /**
   * 古い通知のクリーンアップ
   */
  def cleanupOldNotifications(daysToKeep: Int = 30): Future[Int] = {
    val cutoffDate = LocalDateTime.now().minusDays(daysToKeep)
    val initialCount = notifications.length
    
    notifications.filterInPlace(_.createdAt.isAfter(cutoffDate))
    
    val deletedCount = initialCount - notifications.length
    
    loggingService.logUserAction(
      action = "notifications_cleanup",
      userId = 0L, // システムアクション
      details = Map("deletedCount" -> deletedCount.toString)
    )
    
    Future.successful(deletedCount)
  }

  /**
   * 通知統計の取得
   */
  def getNotificationStats(userId: Long): Future[Map[String, Any]] = {
    val userNotifications = notifications.filter(_.userId == userId)
    
    val stats = Map(
      "totalNotifications" -> userNotifications.length,
      "unreadCount" -> userNotifications.count(_.readAt.isEmpty),
      "readCount" -> userNotifications.count(_.readAt.isDefined),
      "notificationsByType" -> userNotifications.groupBy(_.notificationType).mapValues(_.length),
      "notificationsByPriority" -> userNotifications.groupBy(_.priority).mapValues(_.length),
      "lastNotification" -> userNotifications.maxByOption(_.createdAt).map(_.createdAt.toString),
      "averageNotificationsPerDay" -> calculateAverageNotificationsPerDay(userNotifications)
    )
    
    Future.successful(stats)
  }

  /**
   * 日平均通知数の計算
   */
  private def calculateAverageNotificationsPerDay(notifications: List[Notification]): Double = {
    if (notifications.isEmpty) 0.0
    else {
      val days = java.time.Duration.between(
        notifications.minBy(_.createdAt).createdAt,
        notifications.maxBy(_.createdAt).createdAt
      ).toDays
      
      if (days > 0) notifications.length.toDouble / days else notifications.length.toDouble
    }
  }

  /**
   * 通知設定の管理
   */
  def updateNotificationSettings(
    userId: Long,
    notificationType: NotificationType,
    enabled: Boolean,
    channels: List[NotificationChannel]
  ): Future[Boolean] = {
    // 通知設定の更新処理
    loggingService.logUserAction(
      action = "notification_settings_updated",
      userId = userId,
      details = Map(
        "notificationType" -> notificationType.toString,
        "enabled" -> enabled.toString,
        "channels" -> channels.map(_.toString).mkString(",")
      )
    )
    
    Future.successful(true)
  }
}
