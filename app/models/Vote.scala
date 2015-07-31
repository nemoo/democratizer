package models

import play.api.db.slick.Config.driver.simple._
import java.sql.Timestamp

case class Vote(id: Int, baseline: Int, user: Int, timestamp: Timestamp)

class VoteTable(tag: Tag) extends Table[Vote](tag, "VOTE") {
  def * = (id, baseline, user, timestamp) <> (Vote.tupled, Vote.unapply)
  def ? = (id.?, baseline.?, user.?, timestamp.?).shaped.<>({ r => import r._; _1.map(_ => Vote.tupled((_1.get, _2.get, _3.get, _4.get))) }, (_: Any) => throw new Exception("Inserting into ? projection not supported."))

  val id: Column[Int] = column[Int]("ID", O.AutoInc, O.PrimaryKey)
  val baseline: Column[Int] = column[Int]("BASELINE")
  val user: Column[Int] = column[Int]("USER")
  val timestamp: Column[Timestamp] = column[Timestamp]("TIMESTAMP")
}

object Votes extends DAO {

  def findById(id: Int)(implicit session: Session): Option[Vote] =
    Votes.filter(_.id === id).firstOption

  def findByBaseline(baseline: Int)(implicit session: Session): Option[Vote] =
    Votes.filter(_.baseline === baseline).firstOption

  def findByUser(user: Int)(implicit  session: Session): Option[Vote] =
    Votes.filter(_.user === user).firstOption

  /**
  def findTasks(id: Long)(implicit session: Session): List[Task] =
    Tasks
      .filter(_.project === id)
      .list
    */

  def all(implicit session: Session): List[Vote] =
    Votes.list

  def insert(a: Vote)(implicit session: Session): Int =
    (Votes returning Votes.map(_.id)) += a

}