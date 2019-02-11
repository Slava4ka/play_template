package services

import com.google.inject.Inject
import daos.UserDAO

class UserService @Inject()(userDAO: UserDAO) {
  def findAll() = userDAO.findAll
  def findDeletedUsers() = userDAO.findDeletedUsers()
  def findActiveUsers() = userDAO.findActiveUsers()
  def addUser(name: String, age: Int) = userDAO.addUser(name, age)
  def removeUser(id: Int, name: String) = userDAO.removeUser(id, name)
}
