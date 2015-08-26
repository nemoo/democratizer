package controllers

import play.api.mvc._
import play.api.db.slick._
import play.api.Play.current

object Application extends Controller {

  def ui = Action { implicit rs =>
    DB.withSession{ implicit connection =>
      Ok(views.html.main(5))     
    }
  }

}
