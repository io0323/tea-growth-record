package controllers

import javax.inject._
import play.api.mvc._
import play.api.libs.json._
import play.filters.csrf.CSRF

/**
 * CSRFトークンを提供するコントローラー
 */
@Singleton
class CSRFController @Inject() (
  val controllerComponents: ControllerComponents) extends BaseController {

  def token() = Action { implicit request =>
    CSRF.getToken(request) match {
      case Some(token) => Ok(Json.obj("token" -> JsString(token.value)))
      case None => InternalServerError(Json.obj("error" -> "CSRF token not found"))
    }
  }
}