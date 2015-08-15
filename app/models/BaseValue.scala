package models

import play.api.db.slick.Config.driver.simple._

case class BaseValue(id: Long, baseline: Long, category: String, value: Int, description: String)

class BaseValueTable(tag: Tag) extends Table[BaseValue](tag, "BASEVALUE") {
  def * = (id, baseline, category, value, description) <> (BaseValue.tupled, BaseValue.unapply)
  def ? = (id.?, baseline.?, category.?, value.?, description.?).shaped.<>({ r => import r._; _1.map(_ => BaseValue.tupled((_1.get, _2.get, _3.get, _4.get, _5.get))) }, (_: Any) => throw new Exception("Inserting into ? projection not supported."))

  val id: Column[Long] = column[Long]("ID", O.AutoInc, O.PrimaryKey)
  val baseline: Column[Long] = column[Long]("BASELINE")
  val category: Column[String] = column[String]("CATEGORY")
  val value: Column[Int] = column[Int]("VALUE")
  val description: Column[String] = column[String]("DESCRIPTION")
}

object BaseValues extends DAO {

  def findById(id: Long)(implicit session: Session): Option[BaseValue] =
    BaseValues.filter(_.id === id).firstOption

  def findByBaseline(baseline: Long)(implicit session: Session): List[BaseValue] =
    BaseValues.filter(_.baseline === baseline).list

  def listAll(implicit session: Session): List[BaseValue] =
    BaseValues.list

  def listCount(count: Int)(implicit session: Session): List[BaseValue] =
    BaseValues.list.take(count)

  def insert(a: BaseValue)(implicit session: Session): Long =
    (BaseValues returning BaseValues.map(_.id)) += a

  /**
  def findTasks(id: Long)(implicit session: Session): List[Task] =
    Tasks
      .filter(_.project === id)
      .list
    */
}