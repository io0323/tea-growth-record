package forms

import play.api.data._
import play.api.data.Forms._
import org.joda.time.DateTime
import java.sql.Timestamp
import models.Tea
import org.joda.time.format.DateTimeFormat
import play.api.data.validation.Constraints._
import models.TeaStatus
import play.api.data.Form
import play.api.data.format.Formats._
import java.math.BigDecimal
import models.{TeaType, User}
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import models.TeaStatusType
import java.time.{LocalDate, LocalDateTime}

/**
 * フォームデータを定義するオブジェクト
 * アプリケーション全体で使用されるフォームの定義とバリデーションを管理
 */
object Forms {
  // 日付フォーマット定数
  val DATE_FORMAT = "yyyy-MM-dd"
  private val dateFormatter = DateTimeFormatter.ofPattern(DATE_FORMAT)

  /**
   * ログインフォームのデータ
   * @param email ユーザーのメールアドレス
   * @param password ユーザーのパスワード
   */
  case class LoginData(
    email: String,
    password: String
  )

  /**
   * 登録フォームのデータ
   * @param username ユーザー名
   * @param email メールアドレス
   * @param password パスワード
   */
  case class RegisterData(
    email: String,
    password: String,
    name: Option[String]
  )

  /**
   * パスワードリセットフォームのデータ
   */
  case class ResetPasswordData(
    email: String)

  /**
   * パスワード更新フォームのデータ
   */
  case class UpdatePasswordData(
    currentPassword: String,
    newPassword: String,
    confirmNewPassword: String
  )

  /**
   * プロフィール更新フォームのデータ
   */
  case class UpdateProfileData(
    name: Option[String],
    email: String
  )

  /**
   * お茶作成フォームのデータ
   * @param name お茶の名前
   * @param teaType お茶の種類
   * @param purchaseDate 購入日
   * @param price 価格
   * @param quantity 数量
   * @param notes メモ
   */
  case class CreateTeaData(
    name: String,
    teaTypeId: Long,
    purchaseDate: Option[LocalDate],
    price: Option[BigDecimal],
    quantity: Option[BigDecimal],
    notes: Option[String]
  )

  /**
   * お茶更新フォームのデータ
   * @param id お茶のID
   * @param name お茶の名前
   * @param teaType お茶の種類
   * @param purchaseDate 購入日
   * @param price 価格
   * @param quantity 数量
   * @param notes メモ
   */
  case class UpdateTeaData(
    name: String,
    teaTypeId: Long,
    purchaseDate: Option[LocalDate],
    price: Option[BigDecimal],
    quantity: Option[BigDecimal],
    notes: Option[String],
    status: Option[TeaStatusType.TeaStatusType]
  )

  /**
   * パスワードのバリデーション
   * @param password 検証するパスワード
   * @return バリデーション結果
   */
  def validatePassword(password: String): Boolean = {
    val passwordPattern = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$"
    password.matches(passwordPattern)
  }

  /**
   * メールアドレスのバリデーション
   * @param email 検証するメールアドレス
   * @return バリデーション結果
   */
  def validateEmail(email: String): Boolean = {
    val emailPattern = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
    email.matches(emailPattern)
  }

  /**
   * ログインフォーム
   */
  val loginForm = Form(
    mapping(
      "email" -> email,
      "password" -> nonEmptyText
    )(LoginData.apply)(LoginData.unapply)
  )

  /**
   * 登録フォーム
   */
  val registerForm = Form(
    mapping(
      "email" -> email,
      "password" -> nonEmptyText(minLength = 6),
      "name" -> optional(text)
    )(RegisterData.apply)(RegisterData.unapply)
  )

  /**
   * プロフィール更新フォーム
   */
  val updateProfileForm = Form(
    mapping(
      "name" -> optional(text),
      "email" -> email
    )(UpdateProfileData.apply)(UpdateProfileData.unapply)
  )

  /**
   * パスワード更新フォーム
   */
  val passwordForm = Form(
    mapping(
      "currentPassword" -> nonEmptyText,
      "newPassword" -> nonEmptyText(minLength = 6),
      "confirmNewPassword" -> nonEmptyText
    )(UpdatePasswordData.apply)(UpdatePasswordData.unapply)
      .verifying("新しいパスワードが一致しません", data => data.newPassword == data.confirmNewPassword)
  )

  /**
   * お茶作成フォーム
   */
  val createTeaForm = Form(
    mapping(
      "name" -> nonEmptyText,
      "teaTypeId" -> longNumber,
      "purchaseDate" -> optional(localDate(DATE_FORMAT)),
      "price" -> optional(bigDecimal),
      "quantity" -> optional(bigDecimal),
      "notes" -> optional(text)
    )(CreateTeaData.apply)(CreateTeaData.unapply)
  )

  /**
   * お茶更新フォーム
   */
  val updateTeaForm = Form(
    mapping(
      "name" -> nonEmptyText,
      "teaTypeId" -> longNumber,
      "purchaseDate" -> optional(localDate(DATE_FORMAT)),
      "price" -> optional(bigDecimal),
      "quantity" -> optional(bigDecimal),
      "notes" -> optional(text),
      "status" -> optional(of[TeaStatusType.TeaStatusType])
    )(UpdateTeaData.apply)(UpdateTeaData.unapply)
  )

  /**
   * Joda DateTimeのフォームマッピング
   */
  private def jodaDate(pattern: String = DATE_FORMAT): Mapping[DateTime] = {
    text.verifying("日付の形式が正しくありません", str => {
      try {
        DateTimeFormat.forPattern(pattern).parseDateTime(str)
        true
      } catch {
        case _: IllegalArgumentException => false
      }
    }).transform[DateTime](
      str => DateTimeFormat.forPattern(pattern).parseDateTime(str),
      date => date.toString(pattern)
    )
  }
}