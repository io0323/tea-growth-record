package controllers

import play.api.mvc._
import play.api.libs.json._
import play.api.data.Form
import play.api.data.Forms._
import javax.inject._
import scala.concurrent.{ExecutionContext, Future}
import services._
import models._
import utils.ValidationUtils
import logging.LoggingService
import metrics.MetricsService
import cache.CacheService

/**
 * TypeScriptのAPI機能をScalaに移行したコントローラー
 */
@Singleton
class ApiController @Inject()(
  cc: ControllerComponents,
  teaService: TeaService,
  userService: UserService,
  plantService: PlantService,
  loggingService: LoggingService,
  metricsService: MetricsService,
  cacheService: CacheService
)(implicit ec: ExecutionContext) extends AbstractController(cc) {

  /**
   * お茶一覧取得（ページネーション対応）
   */
  def getTeas(page: Int = 1, pageSize: Int = 10, userId: Option[Long] = None): Action[AnyContent] = Action.async { implicit request =>
    metricsService.incrementRequestCount()
    val startTime = System.currentTimeMillis()
    
    val result = for {
      teas <- teaService.findByUserId(userId.getOrElse(1L)) // TODO: 認証から取得
      paginatedTeas = paginateResults(teas, page, pageSize)
    } yield {
      val response = PaginatedResponse(
        data = paginatedTeas,
        pagination = PaginationInfo(
          page = page,
          pageSize = pageSize,
          totalItems = teas.length,
          totalPages = (teas.length + pageSize - 1) / pageSize,
          hasNext = page * pageSize < teas.length,
          hasPrevious = page > 1
        )
      )
      
      val duration = System.currentTimeMillis() - startTime
      metricsService.recordResponseTime(duration)
      metricsService.incrementTeaOperationCount()
      
      Ok(Json.toJson(response))
    }
    
    result.recover {
      case e: Exception =>
        metricsService.incrementErrorCount()
        loggingService.logError("お茶一覧取得エラー", e)
        InternalServerError(Json.obj("error" -> "お茶一覧の取得に失敗しました"))
    }
  }

  /**
   * お茶詳細取得
   */
  def getTea(id: Long): Action[AnyContent] = Action.async { implicit request =>
    metricsService.incrementRequestCount()
    val startTime = System.currentTimeMillis()
    
    val result = for {
      cachedTea <- cacheService.getCachedTea(id)
      tea <- cachedTea match {
        case Some(cached) => Future.successful(Some(cached))
        case None => teaService.findById(id)
      }
      _ <- tea match {
        case Some(t) => cacheService.cacheTea(id, t)
        case None => Future.successful(())
      }
    } yield {
      val duration = System.currentTimeMillis() - startTime
      metricsService.recordResponseTime(duration)
      
      tea match {
        case Some(t) => Ok(Json.toJson(t))
        case None => NotFound(Json.obj("error" -> "お茶が見つかりません"))
      }
    }
    
    result.recover {
      case e: Exception =>
        metricsService.incrementErrorCount()
        loggingService.logError("お茶詳細取得エラー", e)
        InternalServerError(Json.obj("error" -> "お茶詳細の取得に失敗しました"))
    }
  }

  /**
   * お茶作成
   */
  def createTea(): Action[JsValue] = Action.async(parse.json) { implicit request =>
    metricsService.incrementRequestCount()
    val startTime = System.currentTimeMillis()
    
    request.body.validate[CreateTeaRequest] match {
      case JsSuccess(createRequest, _) =>
        val validationResult = ValidationUtils.validateTeaData(
          createRequest.name,
          createRequest.price.getOrElse(0),
          createRequest.quantity.getOrElse(0),
          createRequest.purchaseDate
        )
        
        if (validationResult.isValid) {
          val tea = models.Tea(
            name = createRequest.name,
            typeId = createRequest.typeId,
            origin = createRequest.origin,
            purchaseDate = java.time.LocalDateTime.parse(createRequest.purchaseDate),
            status = TeaStatus.fromString(createRequest.status),
            description = createRequest.description,
            price = createRequest.price,
            quantity = createRequest.quantity,
            unit = createRequest.unit.map(TeaUnit.fromString),
            imageUrl = createRequest.imageUrl,
            userId = Some(1L) // TODO: 認証から取得
          )
          
          val result = for {
            createdTea <- teaService.create(tea)
            _ <- createdTea match {
              case Some(t) => cacheService.invalidateUserTeas(1L) // TODO: 認証から取得
              case None => Future.successful(())
            }
          } yield {
            val duration = System.currentTimeMillis() - startTime
            metricsService.recordResponseTime(duration)
            metricsService.incrementTeaOperationCount()
            
            createdTea match {
              case Some(t) => Created(Json.toJson(t))
              case None => InternalServerError(Json.obj("error" -> "お茶の作成に失敗しました"))
            }
          }
          
          result.recover {
            case e: Exception =>
              metricsService.incrementErrorCount()
              loggingService.logError("お茶作成エラー", e)
              InternalServerError(Json.obj("error" -> "お茶の作成に失敗しました"))
          }
        } else {
          Future.successful(BadRequest(Json.obj("errors" -> validationResult.errors)))
        }
        
      case JsError(errors) =>
        Future.successful(BadRequest(Json.obj("errors" -> errors)))
    }
  }

  /**
   * お茶更新
   */
  def updateTea(id: Long): Action[JsValue] = Action.async(parse.json) { implicit request =>
    metricsService.incrementRequestCount()
    val startTime = System.currentTimeMillis()
    
    request.body.validate[UpdateTeaRequest] match {
      case JsSuccess(updateRequest, _) =>
        val result = for {
          existingTea <- teaService.findById(id)
          updatedTea <- existingTea match {
            case Some(tea) =>
              val updated = tea.copy(
                name = updateRequest.name.getOrElse(tea.name),
                typeId = updateRequest.typeId.getOrElse(tea.typeId),
                origin = updateRequest.origin.getOrElse(tea.origin),
                purchaseDate = updateRequest.purchaseDate.map(java.time.LocalDateTime.parse).getOrElse(tea.purchaseDate),
                status = updateRequest.status.map(TeaStatus.fromString).getOrElse(tea.status),
                description = updateRequest.description.orElse(tea.description),
                price = updateRequest.price.orElse(tea.price),
                quantity = updateRequest.quantity.orElse(tea.quantity),
                unit = updateRequest.unit.map(TeaUnit.fromString).orElse(tea.unit),
                imageUrl = updateRequest.imageUrl.orElse(tea.imageUrl)
              )
              teaService.update(updated)
            case None => Future.successful(None)
          }
          _ <- updatedTea match {
            case Some(t) => cacheService.invalidateTea(id)
            case None => Future.successful(())
          }
        } yield {
          val duration = System.currentTimeMillis() - startTime
          metricsService.recordResponseTime(duration)
          metricsService.incrementTeaOperationCount()
          
          updatedTea match {
            case Some(t) => Ok(Json.toJson(t))
            case None => NotFound(Json.obj("error" -> "お茶が見つかりません"))
          }
        }
        
        result.recover {
          case e: Exception =>
            metricsService.incrementErrorCount()
            loggingService.logError("お茶更新エラー", e)
            InternalServerError(Json.obj("error" -> "お茶の更新に失敗しました"))
        }
        
      case JsError(errors) =>
        Future.successful(BadRequest(Json.obj("errors" -> errors)))
    }
  }

  /**
   * お茶削除
   */
  def deleteTea(id: Long): Action[AnyContent] = Action.async { implicit request =>
    metricsService.incrementRequestCount()
    val startTime = System.currentTimeMillis()
    
    val result = for {
      deleted <- teaService.delete(id)
      _ <- if (deleted) cacheService.invalidateTea(id) else Future.successful(())
    } yield {
      val duration = System.currentTimeMillis() - startTime
      metricsService.recordResponseTime(duration)
      metricsService.incrementTeaOperationCount()
      
      if (deleted) {
        NoContent
      } else {
        NotFound(Json.obj("error" -> "お茶が見つかりません"))
      }
    }
    
    result.recover {
      case e: Exception =>
        metricsService.incrementErrorCount()
        loggingService.logError("お茶削除エラー", e)
        InternalServerError(Json.obj("error" -> "お茶の削除に失敗しました"))
    }
  }

  /**
   * ユーザー情報取得
   */
  def getUser(id: Long): Action[AnyContent] = Action.async { implicit request =>
    metricsService.incrementRequestCount()
    val startTime = System.currentTimeMillis()
    
    val result = for {
      cachedUser <- cacheService.getCachedUser(id)
      user <- cachedUser match {
        case Some(cached) => Future.successful(Some(cached))
        case None => userService.findById(id)
      }
      _ <- user match {
        case Some(u) => cacheService.cacheUser(id, u)
        case None => Future.successful(())
      }
    } yield {
      val duration = System.currentTimeMillis() - startTime
      metricsService.recordResponseTime(duration)
      
      user match {
        case Some(u) => Ok(Json.toJson(u))
        case None => NotFound(Json.obj("error" -> "ユーザーが見つかりません"))
      }
    }
    
    result.recover {
      case e: Exception =>
        metricsService.incrementErrorCount()
        loggingService.logError("ユーザー情報取得エラー", e)
        InternalServerError(Json.obj("error" -> "ユーザー情報の取得に失敗しました"))
    }
  }

  /**
   * メトリクス取得
   */
  def getMetrics(): Action[AnyContent] = Action.async { implicit request =>
    metricsService.incrementRequestCount()
    
    val metrics = metricsService.getCurrentMetrics()
    val healthMetrics = metricsService.getHealthMetrics()
    val alerts = metricsService.checkAlerts()
    
    val response = Json.obj(
      "metrics" -> Json.toJson(metrics),
      "health" -> Json.toJson(healthMetrics),
      "alerts" -> Json.toJson(alerts)
    )
    
    Future.successful(Ok(response))
  }

  /**
   * ヘルスチェック
   */
  def healthCheck(): Action[AnyContent] = Action.async { implicit request =>
    val healthMetrics = metricsService.getHealthMetrics()
    val alerts = metricsService.checkAlerts()
    
    val status = if (alerts.isEmpty) "healthy" else "warning"
    val response = Json.obj(
      "status" -> status,
      "timestamp" -> java.time.LocalDateTime.now().toString,
      "alerts" -> Json.toJson(alerts)
    )
    
    Future.successful(Ok(response))
  }

  /**
   * ページネーション用のヘルパー関数
   */
  private def paginateResults[T](results: Seq[T], page: Int, pageSize: Int): Seq[T] = {
    val startIndex = (page - 1) * pageSize
    val endIndex = startIndex + pageSize
    results.slice(startIndex, endIndex)
  }
}
