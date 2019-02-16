package daos

import models.User
import org.joda.time.DateTime
import org.w3c.dom.UserDataHandler

class UserDAO {
  def findAll: Seq[User] = {
    UserDAO.users
  }

  def findDeletedUsers: Seq[User] = {
    findAll.filter(_.deleted.isDefined)
  }

  def findActiveUsers: Seq[User] = {
    findAll.filter(_.deleted.isEmpty)
  }

  def addUser(user: User): Unit = {
    UserDAO.counter += 1
    UserDAO.users = UserDAO.users :+ user.copy(id = UserDAO.counter)
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

  def editUser(userCome: User): Unit = {
    val updatedUsers = UserDAO.users
      .map(user => if (user.id == userCome.id) user.copy(name = userCome.name, age = userCome.age) else user)
    UserDAO.users = updatedUsers
    println(UserDAO.users)
  }

}

object UserDAO {
  private var users: Seq[User] = Seq(User(1, "Nick Holland", 25), User(2, "Alex Mcqueen", 26), User(3, "Tom Ford", 28))
  private var counter = users.size
}