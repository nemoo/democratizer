package models

import play.api.db.slick.Config.driver.simple._

case class VoteValue(id: Int, basevalue: Int, vote: Int, delta: Int)

class VoteValueTable(tag: Tag) extends Table[VoteValue](tag, "VOTEVALUE") {
  def * = (id, basevalue, vote, delta) <> (VoteValue.tupled, VoteValue.unapply)
  def ? = (id.?, basevalue.?, vote.?, delta.?).shaped.<>({ r => import r._; _1.map(_ => VoteValue.tupled((_1.get, _2.get, _3.get, _4.get))) }, (_: Any) => throw new Exception("Inserting into ? projection not supported."))

  val id: Column[Int] = column[Int]("ID", O.AutoInc, O.PrimaryKey)
  val basevalue: Column[Int] = column[Int]("BASEVALUE")
  val vote: Column[Int] = column[Int]("VOTE")
  val delta: Column[Int] = column[Int]("DELTA")
}

object VoteValues extends DAO {

  def findById(id: Int)(implicit session: Session): Option[VoteValue] =
    VoteValues.filter(_.id === id).firstOption

  def findByBaseValue(basevalue: Int)(implicit session: Session): Option[VoteValue] =
    VoteValues.filter(_.basevalue === basevalue).firstOption

  def findByVote(vote: Int)(implicit session: Session): Option[VoteValue] =
    VoteValues.filter(_.vote === vote).firstOption

  /**
  def findTasks(id: Long)(implicit session: Session): List[Task] =
    Tasks
      .filter(_.project === id)
      .list
    */

  def all(implicit session: Session): List[VoteValue] =
    VoteValues.list

  def insert(a: VoteValue)(implicit session: Session): Int =
    (VoteValues returning VoteValues.map(_.id)) += a

}