package services

import com.google.inject.Inject
import daos.UserDAO
import models.User
import play.api.libs.json.{JsValue, Json, Writes}

class UserService @Inject()(userDAO: UserDAO) {
  def findAll = userDAO.findAll

  def findDeletedUsers = userDAO.findDeletedUsers

  def findActiveUsers = userDAO.findActiveUsers

  def addUser(name: String, age: Int) = userDAO.addUser(name, age)

  def removeUser(id: Int) = userDAO.removeUser(id)

  def restoreUser(id: Int) = userDAO.restoreUser(id)

  def editUser(id: Int, name: String, age: Int) = userDAO.editUser(id, name, age)

  def makeJsonUser(id: Int): JsValue = {
    implicit val userWrites = new Writes[User] {
      def writes(user: User) = Json.obj(
        "name" -> user.name,
                "age" -> user.age,
      )
    }
    val jsonUser = Json.toJson(userDAO.findActiveUsers(id))
    return jsonUser
  }


}
