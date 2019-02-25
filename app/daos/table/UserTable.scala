package daos.table

import com.github.tototoshi.slick.MySQLJodaSupport._
import org.joda.time.DateTime
import slick.jdbc.MySQLProfile.api._

import models.User


class UserTable(tag: Tag) extends Table[User](tag, "user") {

  def id = column[Int]("id")

  def name = column[String]("name")

  def age = column[Int]("age")

  def deleted = column[Option[DateTime]]("deleted")

  override def * =
    (id, name, age, deleted) <> ((User.apply _).tupled, User.unapply)
}
