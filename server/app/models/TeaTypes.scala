package models

import java.time.LocalDateTime
import play.api.libs.json.{Json, OFormat}
import slick.jdbc.JdbcProfile
import slick.lifted.{TableQuery, Tag}
import slick.jdbc.MySQLProfile.api._

/**
 * お茶の種類を表すケースクラス
 */
case class TeaTypeData(
  id: Option[Long] = None,
  name: String,
  description: Option[String] = None,
  createdAt: Option[LocalDateTime] = None,
  updatedAt: Option[LocalDateTime] = None
)

object TeaTypeData {
  implicit val teaTypeFormat: OFormat[TeaTypeData] = Json.format[TeaTypeData]
}

/**
 * お茶の種類のテーブル定義
 */
class TeaTypes(tag: Tag) extends Table[TeaTypeData](tag, "tea_types") {
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def name = column[String]("name")
  def description = column[Option[String]]("description")
  def createdAt = column[LocalDateTime]("created_at")
  def updatedAt = column[LocalDateTime]("updated_at")

  def * = (
    id.?,
    name,
    description,
    createdAt.?,
    updatedAt.?
  ) <> (TeaTypeData.tupled, TeaTypeData.unapply)
} 