package handlers

import javax.inject._
import play.api.http.DefaultHttpErrorHandler
import play.api._
import play.api.mvc._
import play.api.mvc.Results._
import play.api.routing.Router
import scala.concurrent._

@Singleton
class ErrorHandler @Inject() (
  env: Environment,
  config: Configuration,
  sourceMapper: OptionalSourceMapper,
  router: Provider[Router])(implicit ec: ExecutionContext)
  extends DefaultHttpErrorHandler(env, config, sourceMapper, router) {

  /**
   * クライアントエラーの処理
   */
  override def onClientError(request: RequestHeader, statusCode: Int, message: String): Future[Result] = {
    Future.successful(
      Status(statusCode)(
        views.html.error(statusCode, message)(request)))
  }

  /**
   * サーバーエラーの処理
   */
  override def onServerError(request: RequestHeader, exception: Throwable): Future[Result] = {
    Future.successful(
      InternalServerError(
        views.html.error(500, "Internal server error")(request)))
  }

  /**
   * ルートが見つからない場合の処理
   */
  override def onNotFound(request: RequestHeader, message: String): Future[Result] = {
    Future.successful(
      NotFound(
        views.html.error(404, "Page not found")(request)))
  }
}