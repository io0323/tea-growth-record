package controllers

import javax.inject.{Inject, Singleton}
import play.api.mvc._
import play.api.i18n.I18nSupport
import services.UserService
import forms.Forms
import scala.concurrent.{ExecutionContext, Future}
import play.api.libs.json.Json

@Singleton
class AuthController @Inject()(
  cc: ControllerComponents,
  userService: UserService
)(implicit ec: ExecutionContext) extends AbstractController(cc) with I18nSupport {

  def login() = Action { implicit request =>
    Ok(views.html.login(Forms.loginForm))
  }

  def loginSubmit() = Action.async { implicit request =>
    Forms.loginForm.bindFromRequest().fold(
      formWithErrors => Future.successful(BadRequest(views.html.login(formWithErrors))),
      data => {
        userService.authenticate(data.email, data.password).map {
          case Some(user) =>
            Redirect(routes.HomeController.index())
              .withSession("userId" -> user.id.get.toString)
          case None =>
            BadRequest(views.html.login(Forms.loginForm.withError("email", "メールアドレスまたはパスワードが正しくありません。")))
        }
      }
    )
  }

  def register() = Action { implicit request =>
    Ok(views.html.register(Forms.registerForm))
  }

  def registerSubmit() = Action.async { implicit request =>
    Forms.registerForm.bindFromRequest().fold(
      formWithErrors => Future.successful(BadRequest(views.html.register(formWithErrors))),
      data => {
        userService.create(data.email, data.password, data.name).map {
          case Some(user) =>
            Redirect(routes.HomeController.index())
              .withSession("userId" -> user.id.get.toString)
          case None =>
            BadRequest(views.html.register(Forms.registerForm.withError("email", "このメールアドレスは既に登録されています。")))
        }
      }
    )
  }

  def logout() = Action { implicit request =>
    Redirect(routes.HomeController.index()).withNewSession
  }
} 