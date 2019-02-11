package controllers

import com.google.inject.Inject
import models.UserForm
import play.api.data.Form
import play.api.mvc.{AbstractController, AnyContent, ControllerComponents, Request}
import services.UserService

class UserController @Inject()(cc: ControllerComponents, userService: UserService) extends AbstractController(cc) with play.api.i18n.I18nSupport {

  def index() = Action { implicit request: Request[AnyContent] =>
    val users = userService.findAll()
    Ok(views.html.index(UserForm.form)(users)(userService.findActiveUsers())(userService.findDeletedUsers()))
  }

  def indexPOST() = Action { implicit request =>
    println("***************************************************")
    println(request.body.asFormUrlEncoded)
    println("***************************************************")
    request.body.asFormUrlEncoded.get("action").headOption match {
      case Some("add") => {
        //с обработкой ошибок
        UserForm.form.bindFromRequest.fold(
          formWithErrors => {
            BadRequest(views.html.index(formWithErrors)(userService.findAll())(userService.findActiveUsers())(userService.findDeletedUsers()))
          },
          formData => {
            userService.addUser(formData.name, formData.age)
            val users = userService.findAll()
            println(users.toString())
            Ok(views.html.index(UserForm.form)(users)(userService.findActiveUsers())(userService.findDeletedUsers()))
          }
        )
      }

      case Some("remove") => {
        UserForm.form.bindFromRequest.fold(
          formWithErrors => {
            BadRequest(views.html.index(formWithErrors)(userService.findAll())(userService.findActiveUsers())(userService.findDeletedUsers()))
          },
          formData => {
            println("this_-_-_-_remove")
           // userService.removeUser()
            println(formData.name, formData.age)
            println("this_-_-_-_remove")
            val users = userService.findAll()
            Ok(views.html.index(UserForm.form)(users)(userService.findActiveUsers())(userService.findDeletedUsers()))
          }
        )

/*
        val a = request.body.asFormUrlEncoded.toSeq.map(a => a.map(b => b))
        val aa = a.head.apply("deleteItem").head
        val name1 = aa.

        Ok(a.toString())*/
      }

      case _ => BadRequest("This action is not allowed")
    }
  }


  def addElementToDB() = {
    TODO
  }

  def deleteElementFromDb() = TODO

  def testString() = Action {
    Ok(views.html.testPage(a = "i send this message"))
  }

  def delete(id: Int)() = Action{
    Redirect("/")
  }
}

