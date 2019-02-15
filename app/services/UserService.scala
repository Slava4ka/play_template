package services

import com.google.inject.Inject
import daos.UserDAO
import models.User
import play.api.libs.json.{JsValue, Json, Writes}

class UserService @Inject()(userDAO: UserDAO) {
  def findAll: Seq[User] = userDAO.findAll

  def findDeletedUsers: Seq[User] = userDAO.findDeletedUsers

  def findActiveUsers: Seq[User] = userDAO.findActiveUsers

  def addUser(name: String, age: Int): Unit = userDAO.addUser(name, age)

  def removeUser(id: Int): Unit = userDAO.removeUser(id)

  def restoreUser(id: Int): Unit = userDAO.restoreUser(id)

  def editUser(id: Int, name: String, age: Int): Unit = userDAO.editUser(id, name, age)

  def makeJsonUser(id: Int): JsValue = {
    implicit val userWrites = new Writes[User] {
      def writes(user: User) = Json.obj(
        "name" -> user.name,
                "age" -> user.age,

      )
    }
    val jsonUser = Json.toJson(findActiveUsers.filter(a => a.id == id+1).head)
    jsonUser
  }


}
