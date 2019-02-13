import models.User
import services.UserService
import daos.UserDAO
import org.joda.time.DateTime
import play.api.libs.json._
import play.api.libs.json.JsObject

import scala.util.parsing.json.JSONObject


var temp1: Seq[Int] = Seq(1, 2, 3, 4, 5)
temp1 = temp1.filterNot(_ == 5)

val a = temp1.mkString("\\n")

var users: Seq[User] =
  Seq(User(1, "Nick Holland", 25),
    User(2, "Alex Mcqueen", 26),
    User(3, "Tom Ford", 28))

users.filterNot(a => (a.id == 1) && (a.name == "Nick Holland"))
val l = users.find(a => a.id == 1)

users.foreach(a => {
  if (a.id == 1) {
    val temp = a.copy(deleted = Option(DateTime.now()))
    users = users :+ temp
    users = users.filterNot(_ == a)
  }
})
users.sortBy(a => a.id)

val id = 1

val updatedUsers = users
  .map(user => if (user.id == id) user.copy(name = "Pite", age = 25) else user)


case class Person(name: String, address: Address)

case class Address(city: String, state: String)

val p = Person("Alvin Alexander", Address("Talkeetna", "AK"))

implicit val userWrites = new Writes[User] {
  def writes(user: User) = Json.obj(
    "id" -> user.id,
    "name" -> user.name,
    "age" -> user.age,
  )
}

val jsonUser = users.map(user => Json.toJson(user))
