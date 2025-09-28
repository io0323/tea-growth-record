package models

import java.time.LocalDateTime
import java.sql.Timestamp
import play.api.libs.json._
import slick.jdbc.MySQLProfile.api._
import org.mindrot.jbcrypt.BCrypt
import slick.lifted.ProvenShape
import play.api.libs.json.{Json, OFormat}
import slick.jdbc.JdbcProfile
import slick.lifted.TableQuery

/**
 * ユーザーモデル
 *
 * @param id ユーザーID
 * @param username ユーザー名
 * @param email メールアドレス
 * @param passwordHash パスワードハッシュ
 * @param createdAt 作成日時
 * @param updatedAt 更新日時
 */
case class User(
  id: Option[Long] = None,
  email: String,
  password: String,
  name: Option[String] = None,
  createdAt: Option[LocalDateTime] = None,
  updatedAt: Option[LocalDateTime] = None
) {

  /**
   * パスワードを検証
   */
  def checkPassword(password: String): Boolean = {
    this.password == password // TODO: ハッシュ化したパスワードの比較に変更する
  }
}

/**
 * ユーザーテーブル定義
 */
class Users(tag: Tag) extends Table[User](tag, "users") {
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def email = column[String]("email")
  def password = column[String]("password")
  def name = column[Option[String]]("name")
  def createdAt = column[LocalDateTime]("created_at")
  def updatedAt = column[LocalDateTime]("updated_at")

  def * = (
    id.?,
    email,
    password,
    name,
    createdAt.?,
    updatedAt.?
  ) <> (User.tupled, User.unapply)
}

/**
 * ユーザーのJSONフォーマット
 */
object User {
  implicit val dateTimeFormat: Format[LocalDateTime] = new Format[LocalDateTime] {
    def reads(json: JsValue): JsResult[LocalDateTime] = json.validate[String].map(LocalDateTime.parse)
    def writes(dt: LocalDateTime): JsValue = JsString(dt.toString)
  }

  implicit val userFormat: OFormat[User] = Json.format[User]

  /**
   * ユーザーを作成する
   */
  def create(
    username: String,
    email: String,
    passwordHash: String,
    createdAt: LocalDateTime,
    updatedAt: LocalDateTime
  ): User = {
    User(None, email, passwordHash, None, createdAt, updatedAt)
  }

  def apply(
    id: Option[Long],
    username: String,
    email: String,
    passwordHash: String,
    createdAt: LocalDateTime,
    updatedAt: LocalDateTime
  ): User = new User(id, email, passwordHash, None, createdAt, updatedAt)

  def unapply(user: User): Option[(Option[Long], String, String, Option[String], LocalDateTime, LocalDateTime)] =
    Some((user.id, user.email, user.password, user.name, user.createdAt, user.updatedAt))

  val users = TableQuery[Users]
}