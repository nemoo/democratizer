package models

import play.api.db.slick.Config.driver.simple._

private[models] trait DAO {

  lazy val Baselines = TableQuery[BaselineTable]
  lazy val BaseValues = TableQuery[BaseValueTable]
  lazy val Users = TableQuery[UserTable]
  lazy val Votes = TableQuery[VoteTable]
  lazy val VoteValues = TableQuery[VoteValueTable]
}