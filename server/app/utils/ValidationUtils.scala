package utils

import play.api.data.validation.ValidationError
import play.api.libs.json.{JsPath, JsResult, JsValue, Reads}
import scala.util.matching.Regex

/**
 * 高度なバリデーション機能を提供するユーティリティクラス
 */
object ValidationUtils {

  /**
   * メールアドレスのバリデーション
   */
  val emailRegex: Regex = """^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$""".r

  def validateEmail(email: String): Option[String] = {
    if (emailRegex.findFirstMatchIn(email).isDefined) {
      None
    } else {
      Some("有効なメールアドレスを入力してください")
    }
  }

  /**
   * パスワードの強度バリデーション
   */
  def validatePassword(password: String): Option[String] = {
    val errors = scala.collection.mutable.ListBuffer[String]()
    
    if (password.length < 8) {
      errors += "パスワードは8文字以上である必要があります"
    }
    
    if (!password.exists(_.isUpper)) {
      errors += "パスワードには大文字を含める必要があります"
    }
    
    if (!password.exists(_.isLower)) {
      errors += "パスワードには小文字を含める必要があります"
    }
    
    if (!password.exists(_.isDigit)) {
      errors += "パスワードには数字を含める必要があります"
    }
    
    if (!password.exists("!@#$%^&*()_+-=[]{}|;:,.<>?".contains(_))) {
      errors += "パスワードには特殊文字を含める必要があります"
    }
    
    if (errors.nonEmpty) {
      Some(errors.mkString(", "))
    } else {
      None
    }
  }

  /**
   * お茶の名前のバリデーション
   */
  def validateTeaName(name: String): Option[String] = {
    if (name.trim.isEmpty) {
      Some("お茶の名前を入力してください")
    } else if (name.length > 100) {
      Some("お茶の名前は100文字以内で入力してください")
    } else {
      None
    }
  }

  /**
   * 価格のバリデーション
   */
  def validatePrice(price: BigDecimal): Option[String] = {
    if (price < 0) {
      Some("価格は0以上である必要があります")
    } else if (price > 1000000) {
      Some("価格は1,000,000円以下である必要があります")
    } else {
      None
    }
  }

  /**
   * 数量のバリデーション
   */
  def validateQuantity(quantity: BigDecimal): Option[String] = {
    if (quantity < 0) {
      Some("数量は0以上である必要があります")
    } else if (quantity > 10000) {
      Some("数量は10,000以下である必要があります")
    } else {
      None
    }
  }

  /**
   * 日付のバリデーション
   */
  def validateDate(dateString: String): Option[String] = {
    try {
      import java.time.LocalDate
      import java.time.format.DateTimeFormatter
      
      val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
      val date = LocalDate.parse(dateString, formatter)
      val today = LocalDate.now()
      
      if (date.isAfter(today)) {
        Some("購入日は今日以前の日付である必要があります")
      } else if (date.isBefore(today.minusYears(10))) {
        Some("購入日は10年以内である必要があります")
      } else {
        None
      }
    } catch {
      case _: Exception => Some("有効な日付を入力してください (yyyy-MM-dd)")
    }
  }

  /**
   * 複合バリデーション結果
   */
  case class ValidationResult(
    isValid: Boolean,
    errors: Map[String, String] = Map.empty
  ) {
    def addError(field: String, message: String): ValidationResult = {
      copy(errors = errors + (field -> message))
    }
    
    def hasErrors: Boolean = errors.nonEmpty
  }

  /**
   * お茶データの包括的バリデーション
   */
  def validateTeaData(
    name: String,
    price: BigDecimal,
    quantity: BigDecimal,
    purchaseDate: String
  ): ValidationResult = {
    var result = ValidationResult(isValid = true)
    
    validateTeaName(name).foreach(error => 
      result = result.addError("name", error)
    )
    
    validatePrice(price).foreach(error => 
      result = result.addError("price", error)
    )
    
    validateQuantity(quantity).foreach(error => 
      result = result.addError("quantity", error)
    )
    
    validateDate(purchaseDate).foreach(error => 
      result = result.addError("purchaseDate", error)
    )
    
    result.copy(isValid = !result.hasErrors)
  }

  /**
   * ユーザーデータの包括的バリデーション
   */
  def validateUserData(
    email: String,
    password: String,
    name: Option[String]
  ): ValidationResult = {
    var result = ValidationResult(isValid = true)
    
    validateEmail(email).foreach(error => 
      result = result.addError("email", error)
    )
    
    validatePassword(password).foreach(error => 
      result = result.addError("password", error)
    )
    
    name.foreach { n =>
      if (n.trim.isEmpty) {
        result = result.addError("name", "名前を入力してください")
      } else if (n.length > 50) {
        result = result.addError("name", "名前は50文字以内で入力してください")
      }
    }
    
    result.copy(isValid = !result.hasErrors)
  }
}
