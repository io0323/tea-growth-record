package models

import java.time.{LocalDate, LocalDateTime}
import play.api.libs.json.{Json, OFormat}
import slick.jdbc.JdbcProfile
import slick.lifted.{TableQuery, Tag}
import slick.jdbc.MySQLProfile.api._
import java.math.BigDecimal

/**
 * お茶の種類の列挙型
 */
sealed trait TeaTypeEnum
object TeaTypeEnum {
  case object Green extends TeaTypeEnum
  case object Black extends TeaTypeEnum
  case object Oolong extends TeaTypeEnum
  case object Puer extends TeaTypeEnum
  case object Other extends TeaTypeEnum

  implicit val teaTypeFormat: play.api.libs.json.Format[TeaTypeEnum] = new play.api.libs.json.Format[TeaTypeEnum] {
    def reads(json: play.api.libs.json.JsValue): play.api.libs.json.JsResult[TeaTypeEnum] = json match {
      case play.api.libs.json.JsString("Green") => play.api.libs.json.JsSuccess(Green)
      case play.api.libs.json.JsString("Black") => play.api.libs.json.JsSuccess(Black)
      case play.api.libs.json.JsString("Oolong") => play.api.libs.json.JsSuccess(Oolong)
      case play.api.libs.json.JsString("Puer") => play.api.libs.json.JsSuccess(Puer)
      case play.api.libs.json.JsString("Other") => play.api.libs.json.JsSuccess(Other)
      case _ => play.api.libs.json.JsError("Invalid TeaType")
    }

    def writes(teaType: TeaTypeEnum): play.api.libs.json.JsValue = teaType match {
      case Green => play.api.libs.json.JsString("Green")
      case Black => play.api.libs.json.JsString("Black")
      case Oolong => play.api.libs.json.JsString("Oolong")
      case Puer => play.api.libs.json.JsString("Puer")
      case Other => play.api.libs.json.JsString("Other")
    }
  }
}

/**
 * お茶の情報を表すモデル
 */
case class Tea(
  id: Option[Long] = None,
  userId: Long,
  name: String,
  teaTypeId: Long,
  purchaseDate: Option[LocalDate] = None,
  price: Option[BigDecimal] = None,
  quantity: Option[BigDecimal] = None,
  notes: Option[String] = None,
  status: TeaStatusType.TeaStatusType = TeaStatusType.New,
  createdAt: Option[LocalDateTime] = None,
  updatedAt: Option[LocalDateTime] = None
)

/**
 * お茶のJSONフォーマット
 */
object Tea {
  implicit val teaFormat: OFormat[Tea] = Json.format[Tea]
}

/**
 * お茶のテーブル定義
 */
class Teas(tag: Tag) extends Table[Tea](tag, "teas") {
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def userId = column[Long]("user_id")
  def name = column[String]("name")
  def teaTypeId = column[Long]("tea_type_id")
  def purchaseDate = column[Option[LocalDate]]("purchase_date")
  def price = column[Option[BigDecimal]]("price")
  def quantity = column[Option[BigDecimal]]("quantity")
  def notes = column[Option[String]]("notes")
  def status = column[TeaStatusType.TeaStatusType]("status")
  def createdAt = column[LocalDateTime]("created_at")
  def updatedAt = column[LocalDateTime]("updated_at")

  def * = (
    id.?,
    userId,
    name,
    teaTypeId,
    purchaseDate,
    price,
    quantity,
    notes,
    status,
    createdAt.?,
    updatedAt.?
  ) <> (Tea.tupled, Tea.unapply)

  def user = foreignKey("fk_tea_user", userId, TableQuery[Users])(_.id)
  def teaType = foreignKey("fk_tea_type", teaTypeId, TableQuery[TeaTypes])(_.id)
}

case class CreateTeaData(
  name: String,
  teaType: String,
  origin: String,
  purchaseDate: java.time.LocalDate,
  status: String,
  description: Option[String],
  price: Option[BigDecimal],
  quantity: Option[BigDecimal],
  unit: Option[String],
  imageUrl: Option[String]
)

case class UpdateTeaData(
  name: String,
  teaType: String,
  origin: String,
  purchaseDate: java.time.LocalDate,
  status: String,
  description: Option[String],
  price: Option[BigDecimal],
  quantity: Option[BigDecimal],
  unit: Option[String],
  imageUrl: Option[String]
)