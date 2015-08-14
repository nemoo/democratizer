package controllers

import play.api.mvc._
import play.api.db.slick._
import play.api.Play.current
import play.api.libs.json._
import models._
import models.Implicits._

object REST extends Controller {

  def getUser(profile: String) = Action { implicit rs =>
    DB.withSession { implicit connection =>

      val data = Json.toJson(Users.findByProfile(profile))
      Ok(data)
    }
  }

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

  //TODO if not exists - creating vote and deltas --> else if exists - updating timestamp and deltas
  def postVote = Action(BodyParsers.parse.json) { implicit request =>
    DB.withSession { implicit connection =>

      val result = request.body.validate[Vote]
      result.fold(
        errors => {
          BadRequest(Json.obj("status" -> "KO", "message" -> JsError.toFlatJson(errors)))
        },
        vote => {
          Votes.insert(vote)
          Ok(Json.obj("status" -> "OK", "message" -> ("Vote saved.")))
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
          basevalues.foreach(BaseValues.insert)
          Ok(Json.obj("status" -> "OK", "message" -> (basevalues.length + " basevalues saved.")))
        }
      )
    }
  }

}