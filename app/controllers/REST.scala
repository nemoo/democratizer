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
        BadRequest("Bad Request: User does not exist!")
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

  def getOverview(user: Long) = Action { implicit rs =>
    DB.withSession { implicit connection =>

      if (Users.findById(user).isDefined) {
        val data: List[Overview] = Baselines.listAll.map(baseline => Overview(
          baseline.id,
          baseline.name,
          baseline.revenue,
          baseline.description,
          if (Votes.findByBaselineAndUser(baseline.id, user).isDefined) true else false))

        Ok(Json.toJson(data))

      } else {
        BadRequest("Bad Request: User does not exist!")
      }
    }
  }

  def getVoteview(user: Long, baseline: Long) = Action { implicit rs =>
    DB.withSession { implicit connection =>

      val base = Baselines.findById(baseline)
      val vote = Votes.findByBaselineAndUser(baseline, user)

      if (Users.findById(user).isDefined && base.isDefined) {

        if(vote.isDefined) {
          val data: Voteview = Voteview(
            base.get.name,
            base.get.revenue,
            BaseValues.findByBaseline(baseline).map(basevalue => Bar(
              basevalue.category,
              basevalue.description,
              basevalue.value,
              0, //TODO averagevalue!!!
              VoteValues.findByBaseValueAndVote(basevalue.id, vote.get.id).get.delta //TODO "exception"?
            )))
          Ok(Json.toJson(data))
        } else {
          val data: Voteview = Voteview(
            base.get.name,
            base.get.revenue,
            BaseValues.findByBaseline(baseline).map(basevalue => Bar(
              basevalue.category,
              basevalue.description,
              basevalue.value,
              0, //TODO averagevalue!!!
              0
            )))
          Ok(Json.toJson(data))
        }

      } else {
        BadRequest("Bad Request: Check your User and Baseline!")
      }
    }
  }

  //TODO if not exists - creating vote and deltas --> else if exists - updating timestamp and deltas
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
            Votes.update(vote.get.id)
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



  //***<--*** DEPRECATED ***-->***//

  def getBaselines() = Action { implicit rs =>
    DB.withSession { implicit connection =>

      val data = Json.toJson(Baselines.listAll)
      Ok(data)
    }
  }

  def getSubmittedVotes(user: Long) = Action { implicit rs =>
    DB.withSession { implicit connection =>

      val data = Json.toJson(Votes.findByUser(user))
      Ok(data)
    }
  }

  def getVote(baseline: Long, vote: Long) = Action { implicit rs =>
    DB.withSession { implicit connection =>

      val b = Json.toJson(Baselines.findById(baseline))
      val v = Json.toJson(Votes.findById(vote))
      val values = Json.toJson(BaseValues.findByBaseline(baseline))
      val deltas = Json.toJson(VoteValues.findByVote(vote))

      val data = Json.arr(b, v, values, deltas)
      Ok(data)
    }
  }

  def postVote = Action(BodyParsers.parse.json) { implicit request =>
    DB.withSession { implicit connection =>

      val result = request.body.validate[Vote]
      result.fold(
        errors => {
          BadRequest(Json.obj("status" -> "KO", "message" -> JsError.toFlatJson(errors)))
        },
        vote => {
          Votes.insert(vote)
          Ok(Json.obj("status" -> "OK", "message" -> "Vote saved."))
        }
      )
    }
  }

  def postDeltas = Action(BodyParsers.parse.json) { implicit request =>
    DB.withSession { implicit connection =>

      val result = request.body.validate[Array[VoteValue]]
      result.fold(
        errors => {
          BadRequest(Json.obj("status" -> "KO", "message" -> JsError.toFlatJson(errors)))
        },
        votevalues => {
          votevalues.foreach(VoteValues.insert)
          Ok(Json.obj("status" -> "OK", "message" -> (votevalues.length + " votevalues saved.")))
        }
      )
    }
  }

}