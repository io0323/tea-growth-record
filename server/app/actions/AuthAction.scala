package actions

import javax.inject.Inject
import play.api.mvc._
import scala.concurrent.{ExecutionContext, Future}
import models.User
import services.UserService
import play.api.libs.json.Json
import play.api.mvc.Results.Unauthorized

/**
 * 認証済みユーザーのリクエスト
 */
case class AuthenticatedRequest[A](
  user: User,
  request: Request[A]
) extends WrappedRequest[A](request)

/**
 * 認証アクション
 */
class AuthAction @Inject()(
  val parser: BodyParsers.Default,
  userService: UserService
)(implicit val executionContext: ExecutionContext)
  extends ActionBuilder[AuthenticatedRequest, AnyContent] {

  /**
   * アクションの実行
   */
  override def invokeBlock[A](
    request: Request[A],
    block: AuthenticatedRequest[A] => Future[Result]
  ): Future[Result] = {
    request.session.get("userId").map { userId =>
      userService.findById(userId.toLong).flatMap {
        case Some(user) =>
          block(AuthenticatedRequest(user, request))
        case None =>
          Future.successful(Unauthorized(Json.obj("error" -> "認証に失敗しました")))
      }
    }.getOrElse {
      Future.successful(Unauthorized(Json.obj("error" -> "ログインが必要です")))
    }
  }
} 