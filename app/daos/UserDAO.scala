package daos

import models.User
import org.joda.time.DateTime

class UserDAO {
  def findAll: Seq[User] = {
    UserDAO.users.headOption match {
      case Some(value) => UserDAO.users
      case None => Seq(User(0, "None", 0))
    }
  }

  def findDeletedUsers: Seq[User] = {
    UserDAO.users.filter(_.deleted.isDefined)
  }

  def findActiveUsers: Seq[User] = {
    UserDAO.users.filter(_.deleted.isEmpty)
  }

  def addUser(name: String, age: Int): Unit = {
    val id = UserDAO.users.lastOption match {
      case Some(value) => value.id + 1
      case None => 1
    }
    UserDAO.users = UserDAO.users :+ User(id, name, age)
    println(UserDAO.users)
  }

  def removeUser(id: Int): Unit = {

    val updatedUsers = UserDAO.users
      .map(user => if (user.id == id) user.copy(deleted = Option(DateTime.now)) else user)

    UserDAO.users = updatedUsers
  }

  def restoreUser(id: Int): Unit = {

    val updatedUsers = UserDAO.users
      .map(user => if (user.id == id) user.copy(deleted = None) else user)

    UserDAO.users = updatedUsers
  }

  def editUser(id: Int, name: String, age: Int): Unit = {
    val updatedUsers = UserDAO.users
// т.к. id приходит по индексу в массиве, нужно прибалять 1. Тут сравневается значение, на не позиция в массиве.
      .map(user => if (user.id == (id+1)) user.copy(name = name, age = age) else user)
    println(updatedUsers)
    UserDAO.users = updatedUsers
    println(UserDAO.users)
  }
}

object UserDAO {
  private var users: Seq[User] = Seq(User(1, "Nick Holland", 25), User(2, "Alex Mcqueen", 26), User(3, "Tom Ford", 28))
}