package models

import play.api.libs.json._
import slick.jdbc.SQLiteProfile.api._
import java.sql.Timestamp

case class Plant(
  id: Option[Long] = None,
  name: String,
  species: String,
  userId: Option[Long] = None,
  createdAt: Option[Timestamp] = Some(new Timestamp(System.currentTimeMillis())),
  updatedAt: Option[Timestamp] = Some(new Timestamp(System.currentTimeMillis())))

object Plant {
  implicit val timestampFormat: Format[Timestamp] = new Format[Timestamp] {
    def reads(json: JsValue): JsResult[Timestamp] = json match {
      case JsNumber(value) => JsSuccess(new Timestamp(value.toLong))
      case _ => JsError("Expected JsNumber")
    }
    def writes(timestamp: Timestamp): JsValue = JsNumber(timestamp.getTime)
  }

  implicit val plantFormat: Format[Plant] = Json.format[Plant]
}

class Plants(tag: Tag) extends Table[Plant](tag, "plants") {
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def name = column[String]("name")
  def species = column[String]("species")
  def userId = column[Long]("user_id")
  def createdAt = column[Timestamp]("created_at")
  def updatedAt = column[Timestamp]("updated_at")

  def user = foreignKey("user_fk", userId, TableQuery[Users])(_.id)

  def * = (id.?, name, species, userId.?, createdAt.?, updatedAt.?) <> (
    (Plant.apply _).tupled,
    Plant.unapply)
}
