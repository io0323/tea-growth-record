package models

/**
 * ログインリクエスト
 */
case class LoginRequest(
  email: String,
  password: String
) 