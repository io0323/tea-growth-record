package utils

/**
 * パスワードの強度チェックを行うユーティリティクラス
 */
object PasswordValidator {
  /**
   * パスワードの強度チェック
   * @param password チェックするパスワード
   * @return (isValid: Boolean, message: String) パスワードが有効な場合は(true, "")、無効な場合は(false, エラーメッセージ)
   */
  def validate(password: String): (Boolean, String) = {
    if (password.length < 8) {
      (false, "パスワードは8文字以上である必要があります")
    } else if (!password.exists(_.isUpper)) {
      (false, "パスワードには大文字を含める必要があります")
    } else if (!password.exists(_.isLower)) {
      (false, "パスワードには小文字を含める必要があります")
    } else if (!password.exists(_.isDigit)) {
      (false, "パスワードには数字を含める必要があります")
    } else if (!password.exists(c => !c.isLetterOrDigit)) {
      (false, "パスワードには特殊文字を含める必要があります")
    } else {
      (true, "")
    }
  }
}