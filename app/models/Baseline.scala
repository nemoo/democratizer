package models

import play.api.db.slick.Config.driver.simple._
import org.joda.time.DateTime
import Implicits._

case class Baseline(id: Long, name: String, year: DateTime, description: String)

class BaselineTable(tag: Tag) extends Table[Baseline](tag, "BASELINE") {
  def * = (id, name, year, description) <> (Baseline.tupled, Baseline.unapply)
  def ? = (id.?, name.?, year.?, description.?).shaped.<>({ r => import r._; _1.map(_ => Baseline.tupled((_1.get, _2.get, _3.get, _4.get))) }, (_: Any) => throw new Exception("Inserting into ? projection not supported."))

  val id: Column[Long] = column[Long]("ID", O.AutoInc, O.PrimaryKey)
  val name: Column[String] = column[String]("NAME")
  val year: Column[DateTime] = column[DateTime]("YEAR")
  val description: Column[String] = column[String]("DESCRIPTION")
}

object Baselines extends DAO {

  def findById(id: Long)(implicit session: Session): Option[Baseline] =
    Baselines.filter(_.id === id).firstOption

  def findByName(name: String)(implicit session: Session): Option[Baseline] =
    Baselines.filter(_.name === name).firstOption

  def findByYear(year: DateTime)(implicit session: Session): Option[Baseline] =
    Baselines.filter(_.year === year).firstOption

  /**
  def findTasks(id: Long)(implicit session: Session): List[Task] =
    Tasks
      .filter(_.project === id)
      .list
    */

  def all(implicit session: Session): List[Baseline] =
    Baselines.list

  def insert(a: Baseline)(implicit session: Session): Long =
    (Baselines returning Baselines.map(_.id)) += a

}