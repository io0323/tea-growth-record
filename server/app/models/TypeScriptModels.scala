package models

import play.api.libs.json.{Json, OFormat}
import java.time.LocalDateTime

/**
 * TypeScriptの型定義をScalaに移行したモデル
 */

/**
 * お茶の型定義（TypeScriptから移行）
 */
case class TeaType(
  id: Option[Long] = None,
  name: String,
  description: Option[String] = None,
  category: String,
  origin: Option[String] = None,
  caffeineLevel: Option[String] = None,
  brewingTime: Option[Int] = None,
  temperature: Option[Int] = None,
  createdAt: Option[LocalDateTime] = None,
  updatedAt: Option[LocalDateTime] = None
)

object TeaType {
  implicit val teaTypeFormat: OFormat[TeaType] = Json.format[TeaType]
}

/**
 * お茶のステータス定義（TypeScriptから移行）
 */
sealed trait TeaStatus
case object InStock extends TeaStatus
case object OutOfStock extends TeaStatus
case object Ordered extends TeaStatus
case object Archived extends TeaStatus

object TeaStatus {
  implicit val teaStatusFormat: OFormat[TeaStatus] = Json.format[TeaStatus]
  
  def fromString(status: String): TeaStatus = status match {
    case "在庫あり" => InStock
    case "在庫切れ" => OutOfStock
    case "注文済み" => Ordered
    case "アーカイブ" => Archived
    case _ => InStock
  }
  
  def toString(status: TeaStatus): String = status match {
    case InStock => "在庫あり"
    case OutOfStock => "在庫切れ"
    case Ordered => "注文済み"
    case Archived => "アーカイブ"
  }
}

/**
 * お茶の単位定義（TypeScriptから移行）
 */
sealed trait TeaUnit
case object Gram extends TeaUnit
case object Kilogram extends TeaUnit
case object Ounce extends TeaUnit
case object Pound extends TeaUnit
case object Cup extends TeaUnit
case object Bag extends TeaUnit

object TeaUnit {
  implicit val teaUnitFormat: OFormat[TeaUnit] = Json.format[TeaUnit]
  
  def fromString(unit: String): TeaUnit = unit match {
    case "g" => Gram
    case "kg" => Kilogram
    case "oz" => Ounce
    case "lb" => Pound
    case "cup" => Cup
    case "bag" => Bag
    case _ => Gram
  }
  
  def toString(unit: TeaUnit): String = unit match {
    case Gram => "g"
    case Kilogram => "kg"
    case Ounce => "oz"
    case Pound => "lb"
    case Cup => "cup"
    case Bag => "bag"
  }
}

/**
 * お茶の詳細情報（TypeScriptから移行）
 */
case class TeaDetails(
  id: Option[Long] = None,
  name: String,
  typeId: Long,
  origin: String,
  purchaseDate: LocalDateTime,
  status: TeaStatus,
  description: Option[String] = None,
  price: Option[BigDecimal] = None,
  quantity: Option[BigDecimal] = None,
  unit: Option[TeaUnit] = None,
  imageUrl: Option[String] = None,
  userId: Option[Long] = None,
  createdAt: Option[LocalDateTime] = None,
  updatedAt: Option[LocalDateTime] = None
)

object TeaDetails {
  implicit val teaDetailsFormat: OFormat[TeaDetails] = Json.format[TeaDetails]
}

/**
 * お茶作成リクエスト（TypeScriptから移行）
 */
case class CreateTeaRequest(
  name: String,
  typeId: Long,
  origin: String,
  purchaseDate: String,
  status: String,
  description: Option[String] = None,
  price: Option[BigDecimal] = None,
  quantity: Option[BigDecimal] = None,
  unit: Option[String] = None,
  imageUrl: Option[String] = None
)

object CreateTeaRequest {
  implicit val createTeaRequestFormat: OFormat[CreateTeaRequest] = Json.format[CreateTeaRequest]
}

/**
 * お茶更新リクエスト（TypeScriptから移行）
 */
case class UpdateTeaRequest(
  name: Option[String] = None,
  typeId: Option[Long] = None,
  origin: Option[String] = None,
  purchaseDate: Option[String] = None,
  status: Option[String] = None,
  description: Option[String] = None,
  price: Option[BigDecimal] = None,
  quantity: Option[BigDecimal] = None,
  unit: Option[String] = None,
  imageUrl: Option[String] = None
)

object UpdateTeaRequest {
  implicit val updateTeaRequestFormat: OFormat[UpdateTeaRequest] = Json.format[UpdateTeaRequest]
}

/**
 * ユーザー情報（TypeScriptから移行）
 */
case class UserInfo(
  id: Option[Long] = None,
  email: String,
  name: Option[String] = None,
  profileImage: Option[String] = None,
  preferences: Option[Map[String, String]] = None,
  createdAt: Option[LocalDateTime] = None,
  updatedAt: Option[LocalDateTime] = None
)

object UserInfo {
  implicit val userInfoFormat: OFormat[UserInfo] = Json.format[UserInfo]
}

/**
 * 植物情報（TypeScriptから移行）
 */
case class PlantInfo(
  id: Option[Long] = None,
  name: String,
  species: String,
  userId: Option[Long] = None,
  description: Option[String] = None,
  careInstructions: Option[String] = None,
  imageUrl: Option[String] = None,
  createdAt: Option[LocalDateTime] = None,
  updatedAt: Option[LocalDateTime] = None
)

object PlantInfo {
  implicit val plantInfoFormat: OFormat[PlantInfo] = Json.format[PlantInfo]
}

/**
 * ログインフォーム（TypeScriptから移行）
 */
case class LoginForm(
  email: String,
  password: String
)

object LoginForm {
  implicit val loginFormFormat: OFormat[LoginForm] = Json.format[LoginForm]
}

/**
 * 登録フォーム（TypeScriptから移行）
 */
case class RegisterForm(
  email: String,
  password: String,
  name: String
)

object RegisterForm {
  implicit val registerFormFormat: OFormat[RegisterForm] = Json.format[RegisterForm]
}

/**
 * パスワードリセットリクエスト（TypeScriptから移行）
 */
case class PasswordResetRequest(
  email: String
)

object PasswordResetRequest {
  implicit val passwordResetRequestFormat: OFormat[PasswordResetRequest] = Json.format[PasswordResetRequest]
}

/**
 * パスワード変更リクエスト（TypeScriptから移行）
 */
case class ChangePasswordRequest(
  currentPassword: String,
  newPassword: String,
  confirmPassword: String
)

object ChangePasswordRequest {
  implicit val changePasswordRequestFormat: OFormat[ChangePasswordRequest] = Json.format[ChangePasswordRequest]
}

/**
 * プロフィール更新リクエスト（TypeScriptから移行）
 */
case class UpdateProfileRequest(
  name: Option[String] = None,
  profileImage: Option[String] = None,
  preferences: Option[Map[String, String]] = None
)

object UpdateProfileRequest {
  implicit val updateProfileRequestFormat: OFormat[UpdateProfileRequest] = Json.format[UpdateProfileRequest]
}

/**
 * APIレスポンス（TypeScriptから移行）
 */
case class ApiResponse[T](
  success: Boolean,
  data: Option[T] = None,
  message: Option[String] = None,
  errors: Option[Map[String, String]] = None
)

object ApiResponse {
  implicit def apiResponseFormat[T](implicit format: OFormat[T]): OFormat[ApiResponse[T]] = Json.format[ApiResponse[T]]
}

/**
 * ページネーション情報（TypeScriptから移行）
 */
case class PaginationInfo(
  page: Int,
  pageSize: Int,
  totalItems: Long,
  totalPages: Int,
  hasNext: Boolean,
  hasPrevious: Boolean
)

object PaginationInfo {
  implicit val paginationInfoFormat: OFormat[PaginationInfo] = Json.format[PaginationInfo]
}

/**
 * ページネーション付きレスポンス（TypeScriptから移行）
 */
case class PaginatedResponse[T](
  data: Seq[T],
  pagination: PaginationInfo
)

object PaginatedResponse {
  implicit def paginatedResponseFormat[T](implicit format: OFormat[T]): OFormat[PaginatedResponse[T]] = Json.format[PaginatedResponse[T]]
}
