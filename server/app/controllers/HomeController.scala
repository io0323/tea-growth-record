package controllers

import javax.inject.{Inject, Singleton}
import play.api.mvc._
import play.api.data.Forms._
import play.api.data._
import play.api.i18n._
import play.api.libs.json._
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.filters.csrf._
import models._
import services._
import forms.Forms._
import scala.concurrent.{ExecutionContext, Future}
import play.api.libs.json.Json

@Singleton
class HomeController @Inject()(
  cc: ControllerComponents,
  userService: UserService,
  teaService: TeaService,
  emailService: EmailService,
  messagesApi: MessagesApi
)(implicit ec: ExecutionContext) extends AbstractController(cc) with I18nSupport {

  private def getCsrfToken(implicit request: RequestHeader): CSRF.Token = {
    CSRF.getToken.getOrElse(CSRF.Token("", ""))
  }

  def index() = Action.async { implicit request =>
    request.session.get("userId") match {
      case Some(userId) =>
        for {
          userOpt <- userService.findById(userId.toLong)
          teas <- teaService.findByUserId(userId.toLong)
        } yield {
          userOpt match {
            case Some(user) => Ok(views.html.dashboard(user, teas))
            case None => Redirect(routes.AuthController.login())
          }
        }
      case None => Future.successful(Redirect(routes.AuthController.login()))
    }
  }

  def login() = Action { implicit request =>
    Ok(views.html.login(loginForm, getCsrfToken))
  }

  def loginSubmit() = Action.async { implicit request =>
    loginForm.bindFromRequest().fold(
      formWithErrors => Future.successful(BadRequest(views.html.login(formWithErrors, getCsrfToken))),
      data => {
        userService.authenticate(data.email, data.password).map {
          case Some(user) =>
            Redirect(routes.HomeController.dashboard())
              .withSession("userId" -> user.id.toString)
          case None =>
            BadRequest(views.html.login(loginForm.withError("email", "メールアドレスまたはパスワードが正しくありません。"), getCsrfToken))
        }
      }
    )
  }

  def register() = Action { implicit request =>
    Ok(views.html.register(registerForm, getCsrfToken))
  }

  def registerSubmit() = Action.async { implicit request =>
    registerForm.bindFromRequest().fold(
      formWithErrors => Future.successful(BadRequest(views.html.register(formWithErrors, getCsrfToken))),
      data => {
        userService.createUser(data.username, data.email, data.password).map {
          case Some(user) =>
            Redirect(routes.HomeController.login())
              .flashing("success" -> "登録が完了しました。ログインしてください。")
          case None =>
            BadRequest(views.html.register(registerForm.withError("email", "このメールアドレスは既に使用されています。"), getCsrfToken))
        }
      }
    )
  }

  def logout() = Action { implicit request =>
    Redirect(routes.HomeController.index()).withNewSession
  }

  def createTea() = Action { implicit request =>
    Ok(views.html.createTea(createTeaForm, getCsrfToken))
  }

  def doCreateTea() = Action.async { implicit request =>
    request.session.get("userId").map(_.toLong) match {
      case Some(userId) =>
        createTeaForm.bindFromRequest().fold(
          formWithErrors => Future.successful(BadRequest(views.html.createTea(formWithErrors, getCsrfToken))),
          data => {
            teaService.createTea(userId, data).map {
              case Some(tea) =>
                Redirect(routes.HomeController.dashboard())
                  .flashing("success" -> "お茶を登録しました。")
              case None =>
                BadRequest(views.html.createTea(createTeaForm.withError("", "お茶の登録に失敗しました。"), getCsrfToken))
            }
          }
        )
      case None => Future.successful(Redirect(routes.HomeController.login()))
    }
  }

  def viewTea(id: Long) = Action.async { implicit request =>
    request.session.get("userId").map(_.toLong) match {
      case Some(userId) =>
        teaService.findById(id).map {
          case Some(tea) if tea.userId == userId => Ok(views.html.viewTea(tea))
          case _ => NotFound(views.html.notFound())
        }
      case None => Future.successful(Redirect(routes.HomeController.login()))
    }
  }

  def editTea(id: Long) = Action.async { implicit request =>
    request.session.get("userId").map(_.toLong) match {
      case Some(userId) =>
        teaService.findById(id).map {
          case Some(tea) if tea.userId == userId =>
            Ok(views.html.editTea(id, updateTeaForm.fill(UpdateTeaData(
              name = tea.name,
              teaType = tea.teaType,
              purchaseDate = tea.purchaseDate,
              price = tea.price,
              quantity = tea.quantity,
              status = tea.status
            )), getCsrfToken))
          case _ => NotFound(views.html.notFound())
        }
      case None => Future.successful(Redirect(routes.HomeController.login()))
    }
  }

  def doUpdateTea(id: Long) = Action.async { implicit request =>
    request.session.get("userId").map(_.toLong) match {
      case Some(userId) =>
        updateTeaForm.bindFromRequest().fold(
          formWithErrors => Future.successful(BadRequest(views.html.editTea(id, formWithErrors, getCsrfToken))),
          data => {
            teaService.updateTea(id, userId, data).map {
              case true =>
                Redirect(routes.HomeController.dashboard())
                  .flashing("success" -> "お茶の情報を更新しました。")
              case false =>
                BadRequest(views.html.editTea(id, updateTeaForm.withError("", "お茶の更新に失敗しました。"), getCsrfToken))
            }
          }
        )
      case None => Future.successful(Redirect(routes.HomeController.login()))
    }
  }

  def deleteTea(id: Long) = Action.async { implicit request =>
    request.session.get("userId").map(_.toLong) match {
      case Some(userId) =>
        teaService.deleteTea(id, userId).map {
          case true =>
            Redirect(routes.HomeController.dashboard())
              .flashing("success" -> "お茶を削除しました。")
          case false =>
            BadRequest(views.html.teaList(Seq.empty, getCsrfToken)
              .withError("", "お茶の削除に失敗しました。"))
        }
      case None => Future.successful(Redirect(routes.HomeController.login()))
    }
  }

  def profile() = Action.async { implicit request =>
    request.session.get("userId") match {
      case Some(userId) =>
        userService.findById(userId.toLong).map {
          case Some(user) => Ok(views.html.profile(updateProfileForm.fill(UpdateProfileData(user.name, user.email))))
          case None => Redirect(routes.AuthController.login())
        }
      case None => Future.successful(Redirect(routes.AuthController.login()))
    }
  }

  def updateProfile() = Action.async { implicit request =>
    request.session.get("userId") match {
      case Some(userId) =>
        updateProfileForm.bindFromRequest().fold(
          formWithErrors => Future.successful(BadRequest(views.html.profile(formWithErrors))),
          data => {
            userService.findById(userId.toLong).flatMap {
              case Some(user) =>
                val updatedUser = user.copy(
                  name = data.name,
                  email = data.email
                )
                userService.update(updatedUser).map {
                  case Some(_) => Redirect(routes.HomeController.profile())
                    .flashing("success" -> "プロフィールを更新しました。")
                  case None => BadRequest(views.html.profile(updateProfileForm.fill(data))
                    .flashing("error" -> "プロフィールの更新に失敗しました。"))
                }
              case None => Future.successful(Redirect(routes.AuthController.login()))
            }
          }
        )
      case None => Future.successful(Redirect(routes.AuthController.login()))
    }
  }

  def changePassword() = Action.async { implicit request =>
    request.session.get("userId") match {
      case Some(userId) =>
        changePasswordForm.bindFromRequest().fold(
          formWithErrors => Future.successful(BadRequest(views.html.profile(updateProfileForm)
            .flashing("error" -> "パスワードの変更に失敗しました。"))),
          data => {
            userService.findById(userId.toLong).flatMap {
              case Some(user) if user.checkPassword(data.currentPassword) =>
                userService.updatePassword(userId.toLong, data.newPassword).map {
                  case true => Redirect(routes.HomeController.profile())
                    .flashing("success" -> "パスワードを変更しました。")
                  case false => BadRequest(views.html.profile(updateProfileForm)
                    .flashing("error" -> "パスワードの変更に失敗しました。"))
                }
              case Some(_) => Future.successful(BadRequest(views.html.profile(updateProfileForm)
                .flashing("error" -> "現在のパスワードが正しくありません。")))
              case None => Future.successful(Redirect(routes.AuthController.login()))
            }
          }
        )
      case None => Future.successful(Redirect(routes.AuthController.login()))
    }
  }
}