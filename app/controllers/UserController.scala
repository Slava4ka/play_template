package controllers

import com.google.inject.Inject
import models.{User, UserForm}
import play.api.libs.json.{Json, Writes}
import play.api.mvc.{AbstractController, AnyContent, ControllerComponents, Request}
import services.UserBdService

import play.api.i18n.{I18nSupport, MessagesApi}
import play.api.libs.concurrent.Execution.Implicits._

class UserController @Inject()(cc: ControllerComponents, userBdService: UserBdService) extends AbstractController(cc) with play.api.i18n.I18nSupport {

  def index = Action.async { implicit request =>

    for {
      users <- userBdService.findAll
      activeUsers <- userBdService.findActiveUsers
      deletedUsers <- userBdService.findDeletedUsers
    } yield {
      Ok(views.html.index(UserForm.form)(users)(activeUsers)(deletedUsers))
    } //не получается добавить recover
  }

  def addUser = Action.async { implicit request =>

    request.body.asFormUrlEncoded.get("action").headOption match {
      case Some("add") => {

        UserForm.form.bindFromRequest.fold(
          formWithErrors => {
            for {
              users <- userBdService.findAll
              activeUsers <- userBdService.findActiveUsers
              deletedUsers <- userBdService.findDeletedUsers
            } yield {
              BadRequest(views.html.index(formWithErrors)(users)(activeUsers)(deletedUsers))
            }
          },
          formData => {
            val user = formData.toUser

            userBdService.addUser(user)

            for {
              users <- userBdService.findAll
              activeUsers <- userBdService.findActiveUsers
              deletedUsers <- userBdService.findDeletedUsers
            } yield {
              Ok(views.html.index(UserForm.form)(users)(activeUsers)(deletedUsers))
            }
          }
        )

      }
      //  case _ => BadRequest("This action is not allowed")
    }
  }

  def remove(id: Int) = Action {
    println("в remove")
    userBdService.removeUser(id)
    Redirect(routes.UserController.index())
  }

  def restore(id: Int)() = Action {
    userBdService.restoreUser(id)
    Redirect(routes.UserController.index())
  }

  def generateEditPage() = Action.async { implicit request =>
    for {
      users <- userBdService.findAll
      activeUsers <- userBdService.findActiveUsers
    } yield {
      Ok(views.html.edit(users)(activeUsers))
    }
  }

  /**
    *
    * @param id
    * @return jsValue
    */

  def getElementById(id: Int) = Action.async { request =>
    //вот тут нужно сделать чтоб считывалось только с активных
    // не обрабатываются исключения
    userBdService.findUserByID(id).map { result =>
      val user = result.get
      Ok(Json.toJson(user)(User.writes))
    }
  }

  def edit(id: Int) = Action.async(parse.json(User.reads)) { request =>
    val user = request.body

    println("in edit: " + user.id + " " + user.name + " " + user.age + " " + user.deleted)

    userBdService.editUser(user, id)

    userBdService.findAll.map { x =>
      Ok(Json.toJson(x)(Writes.seq(User.writes))) // преобразует весь список по правилу
    }
  }

}
