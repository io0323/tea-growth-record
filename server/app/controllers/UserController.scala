package controllers

import javax.inject.{Inject, Singleton}
import play.api.mvc._
import play.api.i18n.I18nSupport
import services.UserService
import forms.Forms
import scala.concurrent.{ExecutionContext, Future}
import play.api.libs.json.Json
import actions.AuthAction

@Singleton
class UserController @Inject()(
  cc: ControllerComponents,
  userService: UserService,
  authAction: AuthAction
)(implicit ec: ExecutionContext) extends AbstractController(cc) with I18nSupport {

  def profile() = authAction.async { implicit request =>
    userService.findById(request.user.id.get).map {
      case Some(user) => Ok(views.html.profile(Forms.updateProfileForm.fill(
        Forms.UpdateProfileData(user.name, user.email)
      )))
      case None => Redirect(routes.AuthController.login())
    }
  }

  def updateProfile() = authAction.async { implicit request =>
    Forms.updateProfileForm.bindFromRequest().fold(
      formWithErrors => Future.successful(BadRequest(views.html.profile(formWithErrors))),
      data => {
        userService.findById(request.user.id.get).flatMap {
          case Some(user) =>
            val updatedUser = user.copy(
              name = data.name,
              email = data.email
            )
            userService.update(updatedUser).map {
              case Some(_) => Redirect(routes.HomeController.profile())
                .flashing("success" -> "プロフィールを更新しました。")
              case None => BadRequest(views.html.profile(Forms.updateProfileForm.fill(data))
                .flashing("error" -> "プロフィールの更新に失敗しました。"))
            }
          case None => Future.successful(Redirect(routes.AuthController.login()))
        }
      }
    )
  }

  def changePassword() = authAction.async { implicit request =>
    Forms.changePasswordForm.bindFromRequest().fold(
      formWithErrors => Future.successful(BadRequest(views.html.profile(Forms.updateProfileForm)
        .flashing("error" -> "パスワードの変更に失敗しました。"))),
      data => {
        userService.findById(request.user.id.get).flatMap {
          case Some(user) if user.checkPassword(data.currentPassword) =>
            userService.updatePassword(request.user.id.get, data.newPassword).map {
              case true => Redirect(routes.HomeController.profile())
                .flashing("success" -> "パスワードを変更しました。")
              case false => BadRequest(views.html.profile(Forms.updateProfileForm)
                .flashing("error" -> "パスワードの変更に失敗しました。"))
            }
          case Some(_) => Future.successful(BadRequest(views.html.profile(Forms.updateProfileForm)
            .flashing("error" -> "現在のパスワードが正しくありません。")))
          case None => Future.successful(Redirect(routes.AuthController.login()))
        }
      }
    )
  }
} 