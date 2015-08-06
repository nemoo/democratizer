import java.text.SimpleDateFormat
import org.joda.time.DateTime
import play.api._
import models._
import play.api.db.slick._
import play.api.Play.current

object Global extends GlobalSettings {

  override def onStart(app: Application) {

    
    InitialData.insert()
  }

}

/**
 * Initial set of data to be imported
 * in the sample application.
 */
object InitialData {

  val sdf = new SimpleDateFormat("yyyy-MM-dd")

  def insert() {
    DB.withSession { implicit s: Session =>
      if (Tasks.count == 0) {
        val aid = Projects.insert(Project(99, "first"))
        val aid2 = Projects.insert(Project(99, "second"))
        Projects.insert(Project(99, "third"))
   
        Seq(
          Task(99, "blue", aid),
          Task(99, "red", aid),
          Task(99, "green", aid),
          Task(99, "white", aid2),
          Task(99, "black", aid2)).foreach(Tasks.insert)


      }

      if (Baselines.all.isEmpty && BaseValues.all.isEmpty && Users.all.isEmpty && Votes.all.isEmpty && VoteValues.all.isEmpty) {
        val base1 = Baselines.insert(Baseline(99, "aut", new DateTime(2014, 12, 4, 0, 0), "wow"))


        val baseval1 = BaseValues.insert(BaseValue(99, base1, "Soziales", 500))
        val baseval2 = BaseValues.insert(BaseValue(99, base1, "Rüstung", 300))
        val baseval3 = BaseValues.insert(BaseValue(99, base1, "Wirtschaft", 4000))

        val user1 = Users.insert(User(99,"googleplus.com"))
        val vote1 = Votes.insert(Vote(99,base1,user1,new DateTime(2014,12,3,21,4)))

        Seq(
          VoteValue(99,baseval1,vote1,20),
          VoteValue(99,baseval2,vote1,-200),
          VoteValue(99,baseval3,vote1,-300)).foreach(VoteValues.insert)

      }
    }
  }
}