package models

import java.sql.Date

/**
 * お茶作成リクエスト
 */
case class CreateTeaRequest(
  name: String,
  description: Option[String] = None,
  plantingDate: Option[Date] = None,
  location: Option[String] = None
) 