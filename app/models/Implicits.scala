package models

import play.api.db.slick.Config.driver.simple._
import play.api.libs.json._

object Implicits {

  implicit val dateTimeColumnType = MappedColumnType.base[org.joda.time.DateTime, java.sql.Timestamp](
  { dt => new java.sql.Timestamp(dt.getMillis) },
  { ts => new org.joda.time.DateTime(ts) })

  /**
  implicit val baselineWrites = new Writes[Baseline] {
    def writes(baseline: Baseline) = Json.obj(
      "id" -> baseline.id,
      "name" -> baseline.name,
      "revenue" -> baseline.revenue,
      "description" -> baseline.description
    )
  } */

  implicit val baselineWrites: Writes[Baseline] = Json.writes[Baseline]

  implicit val basevalueWrites: Writes[BaseValue] = Json.writes[BaseValue]

  implicit val voteWrites: Writes[Vote] = Json.writes[Vote]

  implicit val votevaluelineWrites: Writes[VoteValue] = Json.writes[VoteValue]

  implicit val userWrites: Writes[User] = Json.writes[User]

  /**
  implicit val baselineReads: Reads[Baseline] = (
    (JsPath \ "id").read[Long] and
    (JsPath \ "name").read[String] and
    (JsPath \ "revenue").read[Int] and
    (JsPath \ "description").read[String]
    )(Baseline)
  */
  implicit val baselineReads: Reads[Baseline] = Json.reads[Baseline]

  implicit val basevalueReads: Reads[BaseValue] = Json.reads[BaseValue]

  implicit val voteReads: Reads[Vote] = Json.reads[Vote]

  implicit val votevalueReads: Reads[VoteValue] = Json.reads[VoteValue]

  implicit val userReads: Reads[User] = Json.reads[User]

  /** notworking..
  implicit val dateColumnType = MappedColumnType.base[org.joda.time.LocalDate, java.sql.Date](
  { ld => new java.sql.Date(ld.getYear) },
  { da => new org.joda.time.LocalDate(da) })
    */

}