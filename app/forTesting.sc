import models.User
import services.UserService
import daos.UserDAO
import org.joda.time.DateTime

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
