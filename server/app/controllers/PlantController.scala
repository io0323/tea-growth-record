package controllers

import javax.inject._
import play.api.mvc._
import play.api.libs.json._
import scala.concurrent.{ ExecutionContext, Future }
import services.PlantService
import models.Plant
import actions.AuthAction
import play.api.i18n.{I18nSupport, MessagesApi}

@Singleton
class PlantController @Inject() (
  val controllerComponents: ControllerComponents,
  plantService: PlantService,
  authAction: AuthAction,
  val messagesApi: MessagesApi)(implicit ec: ExecutionContext)
  extends BaseController
  with I18nSupport {

  def index() = Action { implicit request =>
    Ok(views.html.index())
  }

  def list() = authAction.async { implicit request =>
    plantService.list().map { plants =>
      Ok(Json.toJson(plants))
    }
  }

  def get(id: Long) = authAction.async { implicit request =>
    plantService.get(id).map {
      case Some(plant) => Ok(Json.toJson(plant))
      case None => NotFound(Json.obj("message" -> "Plant not found"))
    }
  }

  def create() = authAction.async(parse.json) { implicit request =>
    request.body.validate[Plant].fold(
      errors => {
        Future.successful(BadRequest(Json.obj("message" -> "Invalid plant data")))
      },
      plant => {
        plantService.create(plant).map { createdPlant =>
          Created(Json.toJson(createdPlant))
        }
      })
  }

  def update(id: Long) = authAction.async(parse.json) { implicit request =>
    request.body.validate[Plant].fold(
      errors => {
        Future.successful(BadRequest(Json.obj("message" -> "Invalid plant data")))
      },
      plant => {
        plantService.update(id, plant).map {
          case Some(updatedPlant) => Ok(Json.toJson(updatedPlant))
          case None => NotFound(Json.obj("message" -> "Plant not found"))
        }
      })
  }

  def delete(id: Long) = authAction.async { implicit request =>
    plantService.delete(id).map { deleted =>
      if (deleted) Ok(Json.obj("message" -> "Plant deleted"))
      else NotFound(Json.obj("message" -> "Plant not found"))
    }
  }
}
