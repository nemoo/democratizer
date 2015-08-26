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

      if (Baselines.listAll.isEmpty && BaseValues.listAll.isEmpty && Users.listAll.isEmpty && Votes.listAll.isEmpty && VoteValues.listAll.isEmpty) {
        val base1 = Baselines.insert(Baseline(99, "aut", 1000, "Minions ipsum aute jeje daa occaecat wiiiii bappleees. Voluptate reprehenderit tatata bala tu ex bananaaaa poopayee ullamco poopayee sit amet occaecat ullamco. Dolore la bodaaa aliquip tatata bala tu. Esse ut qui sed magna bappleees veniam elit ex reprehenderit pepete. Dolor esse daa hahaha. Jeje incididunt aliquip veniam bappleees voluptate gelatooo daa consequat adipisicing. Consectetur wiiiii chasy eiusmod gelatooo tank yuuu! Hahaha qui tank yuuu! Ullamco commodo ad aliqua ex nostrud occaecat aute uuuhhh."))
        val base2 = Baselines.insert(Baseline(99, "Österreich 2001", 1000, "Minions ipsum hahaha bappleees chasy wiiiii jeje. Po kass bananaaaa para tú me want bananaaa! Poulet tikka masala tank yuuu! Me want bananaaa! baboiii chasy tatata bala tu wiiiii wiiiii jeje tatata bala tu. Para tú me want bananaaa! Para tú ti aamoo! Hana dul sae hahaha hana dul sae la bodaaa ti aamoo! Poulet tikka masala. Gelatooo gelatooo hana dul sae ti aamoo! Gelatooo tulaliloo jeje. Po kass underweaaar tank yuuu! Jeje. Underweaaar tank yuuu! Jiji belloo! Bee do bee do bee do tank yuuu! Belloo! Butt pepete. Pepete uuuhhh belloo! Bananaaaa poopayee uuuhhh. Uuuhhh wiiiii butt baboiii hahaha jeje poopayee ti aamoo! Tulaliloo la bodaaa. "))


        val baseval1 = BaseValues.insert(BaseValue(99, base1, "Soziales", 500, "des"))
        val baseval2 = BaseValues.insert(BaseValue(99, base1, "Rüstung", 300, "descr"))
        val baseval3 = BaseValues.insert(BaseValue(99, base1, "Wirtschaft", 4000, "descrip"))

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