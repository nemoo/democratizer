package models

import play.api.db.slick.Config.driver.simple._

object Implicits {

  implicit val dateTimeColumnType = MappedColumnType.base[org.joda.time.DateTime, java.sql.Timestamp](
  { dt => new java.sql.Timestamp(dt.getMillis) },
  { ts => new org.joda.time.DateTime(ts) })

  /** notworking..
  implicit val dateColumnType = MappedColumnType.base[org.joda.time.LocalDate, java.sql.Date](
  { ld => new java.sql.Date(ld.getYear) },
  { da => new org.joda.time.LocalDate(da) })
    */

}