package models

import play.api.db.slick.Config.driver.simple._
import java.sql.Timestamp

case class Vote(id: Long, baseline: Long, user: Long, timestamp: Timestamp)

class VoteTable(tag: Tag) extends Table[Vote](tag, "VOTE") {
  def * = (id, baseline, user, timestamp) <> (Vote.tupled, Vote.unapply)
  def ? = (id.?, baseline.?, user.?, timestamp.?).shaped.<>({ r => import r._; _1.map(_ => Vote.tupled((_1.get, _2.get, _3.get, _4.get))) }, (_: Any) => throw new Exception("Inserting into ? projection not supported."))

  val id: Column[Long] = column[Long]("ID", O.AutoInc, O.PrimaryKey)
  val baseline: Column[Long] = column[Long]("BASELINE")
  val user: Column[Long] = column[Long]("USER")
  val timestamp: Column[Timestamp] = column[Timestamp]("TIMESTAMP")
}

object Votes extends DAO {

  def findById(id: Long)(implicit session: Session): Option[Vote] =
    Votes.filter(_.id === id).firstOption

  def findByBaseline(baseline: Long)(implicit session: Session): Option[Vote] =
    Votes.filter(_.baseline === baseline).firstOption

  def findByUser(user: Long)(implicit  session: Session): Option[Vote] =
    Votes.filter(_.user === user).firstOption

  /**
  def findTasks(id: Long)(implicit session: Session): List[Task] =
    Tasks
      .filter(_.project === id)
      .list
    */

  def all(implicit session: Session): List[Vote] =
    Votes.list

  def insert(a: Vote)(implicit session: Session): Long =
    (Votes returning Votes.map(_.id)) += a

}