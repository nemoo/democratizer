package models

import play.api.db.slick.Config.driver.simple._

case class VoteValue(id: Long, basevalue: Long, vote: Long, delta: Int)

class VoteValueTable(tag: Tag) extends Table[VoteValue](tag, "VOTEVALUE") {
  def * = (id, basevalue, vote, delta) <> (VoteValue.tupled, VoteValue.unapply)
  def ? = (id.?, basevalue.?, vote.?, delta.?).shaped.<>({ r => import r._; _1.map(_ => VoteValue.tupled((_1.get, _2.get, _3.get, _4.get))) }, (_: Any) => throw new Exception("Inserting into ? projection not supported."))

  val id: Column[Long] = column[Long]("ID", O.AutoInc, O.PrimaryKey)
  val basevalue: Column[Long] = column[Long]("BASEVALUE")
  val vote: Column[Long] = column[Long]("VOTE")
  val delta: Column[Int] = column[Int]("DELTA")
}

object VoteValues extends DAO {

  def findById(id: Long)(implicit session: Session): Option[VoteValue] =
    VoteValues.filter(_.id === id).firstOption

  def findByBaseValue(basevalue: Long)(implicit session: Session): Option[VoteValue] =
    VoteValues.filter(_.basevalue === basevalue).firstOption

  def findByVote(vote: Long)(implicit session: Session): Option[VoteValue] =
    VoteValues.filter(_.vote === vote).firstOption

  //TODO Join (BaseValue+Vote)!

  /**
  def findTasks(id: Long)(implicit session: Session): List[Task] =
    Tasks
      .filter(_.project === id)
      .list
    */

  def all(implicit session: Session): List[VoteValue] =
    VoteValues.list

  def insert(a: VoteValue)(implicit session: Session): Long =
    (VoteValues returning VoteValues.map(_.id)) += a

}