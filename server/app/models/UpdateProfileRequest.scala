package models

/**
 * プロフィール更新リクエスト
 */
case class UpdateProfileRequest(
  name: String,
  email: String
) 