package controllers

import com.google.inject.Inject
import models.{User}
import play.api.mvc.{Action, Controller}
import services.UserBdService
import scala.concurrent.Future
import play.api.i18n.{MessagesApi, Messages, I18nSupport}
import play.api.libs.concurrent.Execution.Implicits._

class UserBDConroller @Inject()(userService: UserBdService, val messagesApi: MessagesApi) extends Controller with I18nSupport {
/*
  def testDBfindAll = Action.async { implicit request =>
    println(userService.findAll)
    userService.findAll map { users => println(users)}

    userService.findAll map { users =>
      Ok(views.html.testBD(users))
    }
  }
*/
  def testDBfindAll = Action.async { request =>
    val usersF = userService.findAll

    println(usersF)
    usersF.map { users =>
      println(users)
      Ok(views.html.testBD(users))
    } recover {
      case _ => NotFound
    }
  }

}
