package services

import com.google.inject.Inject
import daos.UserDAO
import models.User

class UserService @Inject()(userDAO: UserDAO) {
  def findAll: Seq[User] = userDAO.findAll // найти всех

  def findDeletedUsers: Seq[User] = userDAO.findDeletedUsers // найти удаленных пользователей

  def findActiveUsers: Seq[User] = userDAO.findActiveUsers //найти активных пользователей

  def addUser(user: User): Unit = userDAO.addUser(user) // добавить

  def removeUser(id: Int): Unit = userDAO.removeUser(id) // удалить

  def restoreUser(id: Int): Unit = userDAO.restoreUser(id) // восстановить

  def editUser(user: User, id: Int): Unit = { // редактировать пользователя
    val userCopy = user.copy(id = id)
    println("userCopy: " + userCopy)
    userDAO.editUser(userCopy)
  }

}
