package controllers

import javax.inject.{Inject, Singleton}
import play.api.mvc._
import play.api.data.Form
import play.api.i18n.I18nSupport
import play.api.libs.json.Json
import scala.concurrent.{ExecutionContext, Future}
import services.TeaService
import forms.Forms.{CreateTeaData, UpdateTeaData}
import models.Tea
import play.api.libs.Files
import play.api.mvc.MultipartFormData
import actions.AuthAction

/**
 * お茶の管理に関するコントローラー
 */
@Singleton
class TeaController @Inject()(
  cc: ControllerComponents,
  teaService: TeaService,
  authAction: AuthAction
)(implicit ec: ExecutionContext) extends AbstractController(cc) with I18nSupport {

  /**
   * お茶一覧を表示する
   */
  def index() = authAction.async { implicit request =>
    teaService.findByUserId(request.user.id.get).map { teas =>
      Ok(Json.toJson(teas))
    }
  }

  /**
   * お茶の詳細を表示する
   */
  def show(id: Long) = authAction.async { implicit request =>
    teaService.findById(id).map {
      case Some(tea) if tea.userId == request.user.id.get => Ok(Json.toJson(tea))
      case _ => NotFound(Json.obj("error" -> "お茶が見つかりません。"))
    }
  }

  /**
   * 新しいお茶を登録する
   */
  def create() = authAction.async(parse.json) { implicit request =>
    request.body.validate[Forms.CreateTeaData].fold(
      errors => Future.successful(BadRequest(Json.obj("error" -> "不正なリクエストです。"))),
      data => {
        val tea = models.Tea(
          userId = request.user.id.get,
          name = data.name,
          teaTypeId = data.teaTypeId,
          purchaseDate = data.purchaseDate,
          price = data.price,
          quantity = data.quantity,
          notes = data.notes
        )
        teaService.create(tea).map {
          case Some(createdTea) => Created(Json.toJson(createdTea))
          case None => InternalServerError(Json.obj("error" -> "お茶の登録に失敗しました。"))
        }
      }
    )
  }

  /**
   * お茶の情報を更新する
   */
  def update(id: Long) = authAction.async(parse.json) { implicit request =>
    request.body.validate[Forms.UpdateTeaData].fold(
      errors => Future.successful(BadRequest(Json.obj("error" -> "不正なリクエストです。"))),
      data => {
        teaService.findById(id).flatMap {
          case Some(tea) if tea.userId == request.user.id.get =>
            val updatedTea = tea.copy(
              name = data.name,
              teaTypeId = data.teaTypeId,
              purchaseDate = data.purchaseDate,
              price = data.price,
              quantity = data.quantity,
              notes = data.notes,
              status = data.status.getOrElse(tea.status)
            )
            teaService.update(updatedTea).map {
              case Some(updated) => Ok(Json.toJson(updated))
              case None => InternalServerError(Json.obj("error" -> "お茶の更新に失敗しました。"))
            }
          case _ => Future.successful(NotFound(Json.obj("error" -> "お茶が見つかりません。")))
        }
      }
    )
  }

  /**
   * お茶を削除する
   */
  def delete(id: Long) = authAction.async { implicit request =>
    teaService.findById(id).flatMap {
      case Some(tea) if tea.userId == request.user.id.get =>
        teaService.delete(id).map {
          case true => NoContent
          case false => InternalServerError(Json.obj("error" -> "お茶の削除に失敗しました。"))
        }
      case _ => Future.successful(NotFound(Json.obj("error" -> "お茶が見つかりません。")))
    }
  }

  /**
   * お茶の画像をアップロードする
   */
  def uploadImage(id: Long) = authAction.async(parse.multipartFormData) { implicit request =>
    request.body.file("image").map { picture =>
      teaService.uploadImage(id, picture.ref, request.user.id.get).map {
        case Some(tea) => Ok(Json.toJson(tea))
        case None => InternalServerError(Json.obj("error" -> "画像のアップロードに失敗しました"))
      }
    }.getOrElse {
      Future.successful(BadRequest(Json.obj("error" -> "画像が指定されていません")))
    }
  }
} 