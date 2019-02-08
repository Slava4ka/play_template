package controllers

import com.google.inject.Inject
import play.api.mvc.{AbstractController, AnyContent, ControllerComponents, Request}
import services.UserService
import models.UserForm

class UserController @Inject()(cc: ControllerComponents, userService: UserService) extends AbstractController(cc) with play.api.i18n.I18nSupport {

  def index() = Action { implicit request: Request[AnyContent] =>
    val users = userService.findAll()
    Ok(views.html.index(UserForm.form)(users))
  }

  def indexPOST() = Action { implicit request =>

    request.body.asFormUrlEncoded.get("action").headOption match {
      case Some("add") => {
        //с обработкой ошибок
        UserForm.form.bindFromRequest.fold(
          formWithErrors => {
            BadRequest(views.html.index(formWithErrors)(userService.findAll()))
          },
          formData => {
            userService.addUser(formData.id, formData.name, formData.age)
            val users = userService.findAll()
            println(users.toString())
            Ok(views.html.index(UserForm.form)(users))
          }
        )
      }

      case Some("remove") => Ok("Clicked remove")

      case _ => BadRequest("This action is not allowed")
    }
  }


  def addElementToDB() = TODO

  def deleteElementFromDb() = TODO

  def testString() = Action {
    Ok(views.html.testPage(a = "i send this message"))
  }
}
