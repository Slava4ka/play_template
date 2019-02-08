package daos

import models.User

class UserDAO {
  def findAll: Seq[User] = UserDAO.users

  def addUser(name: String, age: Int) = {
    val  id = UserDAO.users.last.id +1
    UserDAO.users = UserDAO.users :+ User(id, name, age)
    println(UserDAO.users)
  }
}

object UserDAO {
  private var users: Seq[User] = Seq(User(1, "Nick Holland", 25), User(2, "Alex Mcqueen", 26), User(3, "Tom Ford", 28))
}