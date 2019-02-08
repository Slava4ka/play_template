import models.User

var temp1 :Seq[Int] = Seq(1,2,3,4,5)
temp1 = temp1.filterNot(_ == 5)

val a = temp1.mkString("\\n")


var users: Seq[User] = Seq(User(1, "Nick Holland", 25), User(2, "Alex Mcqueen", 26), User(3, "Tom Ford", 28))
users.last
users.lastOption