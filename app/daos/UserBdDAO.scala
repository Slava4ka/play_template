package daos

import javax.inject.Inject

import models.User
import daos.table.UserTable

import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile
import slick.jdbc.MySQLProfile.api._
import scala.concurrent.Future


class UserBdDAO @Inject()(
  protected val dbConfigProvider: DatabaseConfigProvider
) extends HasDatabaseConfigProvider[JdbcProfile] {

  private val Users = TableQuery[UserTable]

  def findAll: Future[Seq[User]] = {
    println("findAll " + db.run(Users.result))
    db.run(Users.result)
  }

  def findDeletedUsers: Unit ={
    // TODO:
  }

  def findActiveUsers: Unit ={
    // TODO:
  }

  def addUser(): Unit = {
    // TODO:
  }

  def removeUser: Unit ={
    // TODO:  
  }

  def restoreUser: Unit ={
    // TODO:  
  }

  def editUser: Unit ={
    // TODO:  
  }

}
