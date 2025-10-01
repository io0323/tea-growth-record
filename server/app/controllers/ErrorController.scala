package controllers

import play.api.mvc._
import play.api.i18n.I18nSupport
import play.api.i18n.MessagesApi

import javax.inject._

/**
 * ErrorController handles error pages and error responses.
 */
@Singleton
class ErrorController @Inject()(
  val messagesApi: MessagesApi,
  val controllerComponents: ControllerComponents
) extends BaseController with I18nSupport {

  /**
   * Display forbidden error page.
   */
  def forbidden(): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    Forbidden(views.html.error.forbidden())
  }

  /**
   * Display not found error page.
   */
  def notFound(): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    NotFound(views.html.error.notFound())
  }

  /**
   * Display internal server error page.
   */
  def internalServerError(): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    InternalServerError(views.html.error.internalServerError())
  }
}
