package models

import slick.jdbc.MySQLProfile.api._

object TeaStatusType extends Enumeration {
  type TeaStatusType = Value
  val New = Value("new")
  val InStock = Value("in_stock")
  val OutOfStock = Value("out_of_stock")
  val Consumed = Value("consumed")

  implicit val teaStatusTypeMapper = MappedColumnType.base[TeaStatusType, String](
    teaStatus => teaStatus.toString,
    string => TeaStatusType.withName(string)
  )
} 