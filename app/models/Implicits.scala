package models

import play.api.db.slick.Config.driver.simple._
import play.api.libs.json._

object Implicits {

  implicit val dateTimeColumnType = MappedColumnType.base[org.joda.time.DateTime, java.sql.Timestamp](
  { dt => new java.sql.Timestamp(dt.getMillis) },
  { ts => new org.joda.time.DateTime(ts) })

  implicit val baselineWrites: Writes[Baseline] = Json.writes[Baseline]
  implicit val basevalueWrites: Writes[BaseValue] = Json.writes[BaseValue]
  implicit val voteWrites: Writes[Vote] = Json.writes[Vote]
  implicit val votevaluelineWrites: Writes[VoteValue] = Json.writes[VoteValue]
  implicit val userWrites: Writes[User] = Json.writes[User]

  implicit val baselineReads: Reads[Baseline] = Json.reads[Baseline]
  implicit val basevalueReads: Reads[BaseValue] = Json.reads[BaseValue]
  implicit val voteReads: Reads[Vote] = Json.reads[Vote]
  implicit val votevalueReads: Reads[VoteValue] = Json.reads[VoteValue]
  implicit val userReads: Reads[User] = Json.reads[User]

}