package models

import play.api.db.slick.Config.driver.simple._

case class User(id: Int, profile: String)

class UserTable(tag: Tag) extends Table[User](tag, "USER") {
  def * = (id, profile) <> (User.tupled, User.unapply)
  def ? = (id.?, profile.?).shaped.<>({ r => import r._; _1.map(_ => User.tupled((_1.get, _2.get))) }, (_: Any) => throw new Exception("Inserting into ? projection not supported."))

  val id: Column[Int] = column[Int]("ID", O.AutoInc, O.PrimaryKey)
  val profile: Column[String] = column[String]("PROFILE")
}

object Users extends DAO {

  def findById(id: Int)(implicit session: Session): Option[User] =
    Users.filter(_.id === id).firstOption

  /**
  def findTasks(id: Long)(implicit session: Session): List[Task] =
    Tasks
      .filter(_.project === id)
      .list
    */

  def all(implicit session: Session): List[User] =
    Users.list

  def insert(a: User)(implicit session: Session): Int =
    (Users returning Users.map(_.id)) += a

}