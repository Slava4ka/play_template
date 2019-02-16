package services

import com.google.inject.Inject
import daos.UserDAO
import models.User

class UserService @Inject()(userDAO: UserDAO) {
  def findAll: Seq[User] = userDAO.findAll

  def findDeletedUsers: Seq[User] = userDAO.findDeletedUsers

  def findActiveUsers: Seq[User] = userDAO.findActiveUsers

  def addUser(user: User): Unit = userDAO.addUser(user)

  def removeUser(id: Int): Unit = userDAO.removeUser(id)

  def restoreUser(id: Int): Unit = userDAO.restoreUser(id)

  def editUser(user: User, id: Int): Unit = {
    val userCopy = user.copy(id = id)
    println("userCopy: "+ userCopy)
    userDAO.editUser(userCopy)
  }


}
