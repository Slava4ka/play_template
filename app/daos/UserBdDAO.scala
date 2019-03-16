package daos

import javax.inject.Inject

import models.User
import daos.table.UserTable
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.{JdbcProfile, JdbcType}
import slick.jdbc.MySQLProfile.api._
import scala.concurrent.{ExecutionContext, Future}

import org.joda.time.DateTime
import slick.ast.BaseTypedType


class UserBdDAO @Inject()(
  protected val dbConfigProvider: DatabaseConfigProvider
)(implicit ec: ExecutionContext) extends HasDatabaseConfigProvider[JdbcProfile] {

  private val users = TableQuery[UserTable]

  implicit val dtMapper: JdbcType[DateTime] with BaseTypedType[DateTime] = MappedColumnType.base[DateTime, Long](d => d.getMillis, d => new DateTime(d))

  def findAll: Future[Seq[User]] = {
    println("findAll")
    db.run(users.result)
  }

  def findUserByID(id: Int): Future[Option[User]] = {
   // db.run(users.filter(x => x.id === id && x.deleted.isEmpty).result.headOption)
    db.run(users.filter(x => x.id === id).result.headOption)
  }

  def findDeletedUsers: Future[Seq[User]] = {
    println("findDeletedUsers")
    findAll.map(user => user.filter(_.deleted.isDefined))
  }

  def findActiveUsers: Future[Seq[User]] = {
    println("findActiveUsers")
    findAll.map(user => user.filter(_.deleted.isEmpty))
  }

  def addUser(user: User): Future[String] = {
    db.run(users += user).map(res => "User successfully added").recover {
      case ex: Exception => ex.getCause.getMessage
    }
  }

  def removeUser(id: Int): Future[Int] = {
    db.run(users.filter(_.id === id).map(_.deleted).update(Option(DateTime.now)))
  }

  def restoreUser(id: Int): Future[Int] = {
    db.run(users.filter(_.id === id).map(_.deleted).update(None))
  }

  def editUser(user: User): Future[Int] = {
    // slick 2.X
    // db.run(users.filter(_.id === user.id).update(user))
    //insertOrUpdate выполняется путем предоставления строки для вставки или обновления.
    // Объект должен содержать первичный ключ таблицы,
    // так как часть обновления должна быть в состоянии найти уникально подходящую строку.
    println("re")
    db.run(users.insertOrUpdate(user))
  }

}
