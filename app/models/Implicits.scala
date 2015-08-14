package models

import play.api.db.slick.Config.driver.simple._
import play.api.libs.json._
import play.api.libs.functional.syntax._

object Implicits {

  implicit val dateTimeColumnType = MappedColumnType.base[org.joda.time.DateTime, java.sql.Timestamp](
  { dt => new java.sql.Timestamp(dt.getMillis) },
  { ts => new org.joda.time.DateTime(ts) })

  implicit val baselineWrites = new Writes[Baseline] {
    def writes(baseline: Baseline) = Json.obj(
      "id" -> baseline.id,
      "name" -> baseline.name,
      "revenue" -> baseline.revenue,
      "description" -> baseline.description
    )
  }

  implicit val basevalueWrites = new Writes[BaseValue] {
    def writes(basevalue: BaseValue) = Json.obj(
      "id" -> basevalue.id,
      "baseline" -> basevalue.baseline,
      "category" -> basevalue.category,
      "value" -> basevalue.value
    )
  }

  implicit val voteWrites = new Writes[Vote] {
    def writes(vote: Vote) = Json.obj(
      "id" -> vote.id,
      "baseline" -> vote.baseline,
      "user" -> vote.user,
      "timestamp" -> vote.timestamp
    )
  }

  implicit val votevaluelineWrites = new Writes[VoteValue] {
    def writes(votevalue: VoteValue) = Json.obj(
      "id" -> votevalue.id,
      "basevalue" -> votevalue.basevalue,
      "vote" -> votevalue.vote,
      "delta" -> votevalue.delta
    )
  }

  implicit val userWrites = new Writes[User] {
    def writes(user: User) = Json.obj(
      "id" -> user.id,
      "profile" -> user.profile
    )
  }

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