package models

import play.api.db.slick.Config.driver.simple._

case class User(id: Long, profile: String)

class UserTable(tag: Tag) extends Table[User](tag, "USER") {
  def * = (id, profile) <> (User.tupled, User.unapply)
  def ? = (id.?, profile.?).shaped.<>({ r => import r._; _1.map(_ => User.tupled((_1.get, _2.get))) }, (_: Any) => throw new Exception("Inserting into ? projection not supported."))

  val id: Column[Long] = column[Long]("ID", O.AutoInc, O.PrimaryKey)
  val profile: Column[String] = column[String]("PROFILE")
}

object Users extends DAO {

  def findById(id: Long)(implicit session: Session): Option[User] =
    Users.filter(_.id === id).firstOption

  def findByProfile(profile: String)(implicit session: Session): Option[User] =
    Users.filter(_.profile === profile).firstOption

  def listAll(implicit session: Session): List[User] =
    Users.list

  def listCount(count: Int)(implicit session: Session): List[User] =
    Users.list.take(count)

  def insert(a: User)(implicit session: Session): Long =
    (Users returning Users.map(_.id)) += a

  /**
  def findTasks(id: Long)(implicit session: Session): List[Task] =
    Tasks
      .filter(_.project === id)
      .list
    */

}