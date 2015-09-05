package controllers

import play.api.libs.json._

case class OverviewItem(baselineId: Long, name: String, description: String, submitted: Boolean)

case class Bar(category: String, description: String, basevalueId: Long, basevalue: Long, averagevalue: Long, delta: Option[Long], modified: Boolean)
case class Voteview(baselineId: Long, name: String, revenue: Long, bars: List[Bar])

case class Submission(user: Long, baseline: Long, basevalue: Long, delta: Long)



object DTO {

  implicit val overviewWrites: Writes[OverviewItem] = Json.writes[OverviewItem]
  implicit val barWrites: Writes[Bar] = Json.writes[Bar]
  implicit val voteviewWrites: Writes[Voteview] = Json.writes[Voteview]
  implicit val submissionWrites: Writes[Submission] = Json.writes[Submission]


  implicit val overviewReads: Reads[OverviewItem] = Json.reads[OverviewItem]
  implicit val barReads: Reads[Bar] = Json.reads[Bar]
  implicit val voteviewReads: Reads[Voteview] = Json.reads[Voteview]
  implicit val submissionReads: Reads[Submission] = Json.reads[Submission]

}