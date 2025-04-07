import play.api.http.DefaultHttpErrorHandler
import play.api._
import play.api.mvc._
import play.api.mvc.Results._
import play.api.routing.Router
import scala.concurrent._
import javax.inject._
import play.api.mvc.Results.InternalServerError
import play.api.libs.json.Json
import play.api.http.Status._

@Singleton
class Global @Inject() (
  env: Environment,
  config: Configuration,
  sourceMapper: OptionalSourceMapper,
  router: Provider[Router]) extends DefaultHttpErrorHandler(env, config, sourceMapper, router) {

  override def onClientError(request: RequestHeader, statusCode: Int, message: String): Future[Result] = {
    Future.successful {
      val result = statusCode match {
        case BAD_REQUEST => Json.obj("error" -> "Bad Request", "message" -> message)
        case FORBIDDEN => Json.obj("error" -> "Forbidden", "message" -> "You are not authorized to access this resource")
        case NOT_FOUND => Json.obj("error" -> "Not Found", "message" -> "The requested resource could not be found")
        case _ => Json.obj("error" -> "Client Error", "message" -> message)
      }
      Status(statusCode)(result)
    }
  }

  override def onServerError(request: RequestHeader, exception: Throwable): Future[Result] = {
    Future.successful {
      val result = Json.obj(
        "error" -> "Internal Server Error",
        "message" -> "A server error occurred")
      InternalServerError(result)
    }
  }
}