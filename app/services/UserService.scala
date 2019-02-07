package services

import com.google.inject.Inject
import daos.UserDAO

class UserService @Inject()(userDAO: UserDAO) {
  def findAll() = userDAO.findAll
  def addUser(id: Int, name: String, age: Int) = userDAO.addUser(id, name, age)
}
