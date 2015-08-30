package controllers

import org.joda.time.DateTime
import play.api.mvc._
import play.api.db.slick._
import play.api.Play.current
import play.api.libs.json._
import models._
import models.Implicits._
import controllers.DTO._

object REST extends Controller {

  def postBaseline = Action(BodyParsers.parse.json) { implicit request =>
    DB.withSession { implicit connection =>

      val result = request.body.validate[Baseline]
      result.fold(
        errors => {
          BadRequest(Json.obj("status" -> "KO", "message" -> JsError.toFlatJson(errors)))
        },
        baseline => {
          Baselines.insert(baseline)
          Ok(Json.obj("status" -> "OK", "message" -> ("Baseline '" + baseline.name + "' saved.")))
        }
      )
    }
  }

  def postBaseValues = Action(BodyParsers.parse.json) { implicit request =>
    DB.withSession { implicit connection =>

      val result = request.body.validate[Array[BaseValue]]
      result.fold(
        errors => {
          BadRequest(Json.obj("status" -> "KO", "message" -> JsError.toFlatJson(errors)))
        },
        basevalues => {
          //TODO check if baseline already has basevalues
          basevalues.foreach(BaseValues.insert)
          Ok(Json.obj("status" -> "OK", "message" -> (basevalues.length + " basevalues saved.")))
        }
      )
    }
  }

  def getUser(profile: String) = Action { implicit rs =>
    DB.withSession { implicit connection =>

      val user = Users.findByProfile(profile)
      if(user.isDefined) {
        Ok(Json.toJson(user))

      } else {
        BadRequest(Json.obj("status" -> "KO", "message" -> "User does not exist!"))
      }
    }
  }

  def postUser = Action(BodyParsers.parse.json) { implicit request =>
    DB.withSession { implicit connection =>

      val result = request.body.validate[User]
      result.fold(
        errors => {
          BadRequest(Json.obj("status" -> "KO", "message" -> JsError.toFlatJson(errors)))
        },
        user => {
          Users.insert(user)
          Ok(Json.obj("status" -> "OK", "message" -> ("User '" + user.profile + "' saved.")))
        }
      )
    }
  }

  def anonymizeUser(user: Long) = Action { implicit rs =>
    DB.withSession { implicit connection =>

      if(Users.findById(user).isDefined) {
        Users.anonymize(user)
        Ok(Json.obj("status" -> "OK", "message" -> "User anonymized."))

      } else {
        BadRequest("Bad Request: User does not exist!")
      }
    }
  }

  def getOverview(user: Long) = Action { implicit rs =>
    DB.withSession { implicit connection =>

      if (Users.findById(user).isDefined) {
        val data: List[OverviewItem] = Baselines.listAll.map( baseline => 
          OverviewItem(
            baseline.id,
            baseline.name,
            baseline.description,
            Votes.findByBaselineAndUser(baseline.id, user).isDefined))

        Ok(Json.toJson(data))

      } else {
        BadRequest(Json.obj("status" -> "KO", "message" -> "User does not exist!"))
      }
    }
  }

  def getVoteview(user: Long, baseline: Long) = Action { implicit rs =>
    DB.withSession { implicit connection =>

      val base = Baselines.findById(baseline)
      val vote = Votes.findByBaselineAndUser(baseline, user)

      if (Users.findById(user).isDefined && base.isDefined) {

        val bars = BaseValues.findByBaseline(baseline).map{ basevalue =>

          val delta = vote match {
            case Some(vote) =>
              val potentialVoteValue = VoteValues.findByBaseValueAndVote(basevalue.id, vote.id)
              potentialVoteValue.map(_.delta)
            case None          => None
          }

          Bar(basevalue.category,
            basevalue.description,
            basevalue.id,
            basevalue.value,
            VoteValues.getAverage(basevalue.id),
            delta,
            false)
        }

        val data: Voteview = Voteview(
          base.get.name,
          base.get.revenue,
          bars)

        Ok(Json.toJson(data))

      } else {
        BadRequest(Json.obj("status" -> "KO", "message" -> "Bad Request: Check your User and Baseline!"))
      }
    }
  }

  def postSubmission() = Action(BodyParsers.parse.json) { implicit request =>
    DB.withSession { implicit connection =>

      val result = request.body.validate[Array[Submission]]
      result.fold(
        errors => {
          BadRequest(Json.obj("status" -> "KO", "message" -> JsError.toFlatJson(errors)))
        },
        submission => {

          val vote = Votes.findByBaselineAndUser(submission(0).baseline,submission(0).user)
          if (vote.isEmpty) {
            submission.foreach(s => VoteValues.insert(VoteValue(
              99,
              s.basevalue,
              Votes.insert(Vote(99, submission(0).baseline, submission(0).user, new DateTime())),
              s.delta)))
            Ok(Json.obj("status" -> "OK", "message" -> "Vote saved."))

          } else {
            Votes.refreshTimestamp(vote.get.id)
            submission.foreach(s => VoteValues.update(
              VoteValues.findByBaseValueAndVote(s.basevalue, vote.get.id).get.id, //TODO "exception"?
              s.delta
            ))
            Ok(Json.obj("status" -> "OK", "message" -> "Vote saved."))
          }
        }
      )
    }
  }


}