package controllers

import com.google.inject.Inject
import models.UserForm
import play.api.mvc.{AbstractController, AnyContent, ControllerComponents, Request}
import services.UserService

class UserController @Inject()(cc: ControllerComponents, userService: UserService) extends AbstractController(cc) with play.api.i18n.I18nSupport {

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
            userService.addUser(formData.name, formData.age)
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
    println(id)
    println(userService.makeJsonUser(id))
    println(userService.findActiveUsers)
    Ok(userService.makeJsonUser(id))
  }

  def edit = Action { request =>
    val json = request.body.asJson.get
    val id = (json \ "id").as[Int]
    val name = (json \ "name").as[String]
    val age = (json \ "age").as[String].toInt
    userService.editUser(id, name, age)
    Ok(userService.findAll(id).toString)
  }

}
