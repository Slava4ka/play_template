package controllers

import com.google.inject.Inject
import models.{User, UserForm}
import play.api.libs.json.{Json, Writes}
import play.api.mvc.{AbstractController, AnyContent, ControllerComponents, Request}
import services.{UserService, UserBdService}

class UserController @Inject()(cc: ControllerComponents, userService: UserService, userBdService: UserBdService) extends AbstractController(cc) with play.api.i18n.I18nSupport {

  def index() = Action { implicit request: Request[AnyContent] =>
    val users = userService.findAll
    Ok(views.html.index(UserForm.form)(users)(userService.findActiveUsers)(userService.findDeletedUsers))
  }

  def indexPOST() = Action { implicit request =>
    request.body.asFormUrlEncoded.get("action").headOption match {
      case Some("add") => {
        //с обработкой ошибок
        UserForm.form.bindFromRequest.fold(
          formWithErrors => {
            BadRequest(views.html.index(formWithErrors)(userService.findAll)(userService.findActiveUsers)(userService.findDeletedUsers))
          },
          formData => {
            val user = formData.toUser
            userService.addUser(user)
            Ok(views.html.index(UserForm.form)(userService.findAll)(userService.findActiveUsers)(userService.findDeletedUsers))
          }
        )
      }
      case _ => BadRequest("This action is not allowed")
    }
  }

  def remove(id: Int)() = Action {
    userService.removeUser(id)
    Redirect(routes.UserController.index())
  }

  def restore(id: Int)() = Action {
    userService.restoreUser(id)
    Redirect(routes.UserController.index())
  }

  def generateEditPage() = Action {
    Ok(views.html.edit(userService.findAll)(userService.findActiveUsers))
  }

  /**
    *
    * @param id
    * @return jsValue
    */

  def getElementById(id: Int) = Action {
    val tempUser = userService.findActiveUsers.filter(a => a.id == id).head

    println(id)
    println(Json.toJson(tempUser)(User.writes))
    println(userService.findActiveUsers)

    Ok(Json.toJson(tempUser)(User.writes))
  }

  def edit(id: Int) = Action(parse.json(User.reads)) { request =>
    val user = request.body

    println(user.id + " " + user.name + " " + user.age + " " + user.deleted)

    userService.editUser(user, id)
    val users = userService.findAll
    Ok(Json.toJson(users)(Writes.seq(User.writes))) // преобразует весь список по правилу
  }
}








