package daos

import models.User

class UserDAO {
  def findAll: Seq[User] = {
    UserDAO.users.headOption match {
      case Some(value) => UserDAO.users
      case None => Seq(User(0, "None", 0))
    }
  }

  def findDeletedUsers() = {
    UserDAO.users.filter(_.deleted.isDefined)
  }

  def findActiveUsers() = {
    UserDAO.users.filter(_.deleted.isEmpty)
  }

  def addUser(name: String, age: Int) = {
    val id = UserDAO.users.lastOption match {
      case Some(value) => value.id + 1
      case None => 1
    }
    UserDAO.users = UserDAO.users :+ User(id, name, age)
    println(UserDAO.users)
  }

  def removeUser(): Unit = {
    // TODO:
  }

  def restoreUser() = {

    // TODO:
  }

  def editUser() = {
    // TODO:
  }
}

object UserDAO {
  private var users: Seq[User] = Seq(User(1, "Nick Holland", 25), User(2, "Alex Mcqueen", 26), User(3, "Tom Ford", 28))
}