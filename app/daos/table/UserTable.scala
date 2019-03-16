package daos.table

import com.github.tototoshi.slick.MySQLJodaSupport._
import org.joda.time.DateTime
import slick.jdbc.MySQLProfile.api._
import models.User


class UserTable(tag: Tag) extends Table[User](tag, "user") {

  def id: Rep[Int] = column[Int]("id")

  def name: Rep[String] = column[String]("name")

  def age: Rep[Int] = column[Int]("age")

  def deleted: Rep[Option[DateTime]] = column[Option[DateTime]]("deleted")

  override def * =
  (id, name, age, deleted) <> ((User.apply _).tupled, User.unapply)
}
