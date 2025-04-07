package models

/**
 * 新規登録リクエスト
 */
case class RegisterRequest(
  name: String,
  email: String,
  password: String
) 