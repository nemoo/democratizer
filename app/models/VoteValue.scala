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

  def findByBaseValueAndVote(basevalue: Long, vote: Long)(implicit session: Session): Option[VoteValue] =
    VoteValues.filter(_.vote === vote).filter(_.basevalue === basevalue).firstOption

  def findByBaseValue(basevalue: Long)(implicit session: Session): List[VoteValue] =
    VoteValues.filter(_.basevalue === basevalue).list

  def findByVote(vote: Long)(implicit session: Session): List[VoteValue] =
    VoteValues.filter(_.vote === vote).list

  def listAll(implicit session: Session): List[VoteValue] =
    VoteValues.list

  def listCount(count: Int)(implicit session: Session): List[VoteValue] =
    VoteValues.list.take(count)

  def insert(a: VoteValue)(implicit session: Session): Long =
    (VoteValues returning VoteValues.map(_.id)) += a

  def update(id: Long, newDelta: Int)(implicit session: Session): Int =
    VoteValues.filter(_.id === id).map(_.delta).update(newDelta) //TODO returning VoteValues.map(_.id))?

  def getAverage(basevalue: Long)(implicit session: Session): Int = {
    val deltas = findByBaseValue(basevalue).map(v => v.delta)
    if(deltas.nonEmpty) deltas.sum / deltas.length else 0
  }


  /**
  def findTasks(id: Long)(implicit session: Session): List[Task] =
    Tasks
      .filter(_.project === id)
      .list
    */
}