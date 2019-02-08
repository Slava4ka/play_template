package models

import play.api.data.Form
import play.api.data.Forms._

case class UserForm(name: String, age: Int)


object UserForm {
  val form: Form[UserForm] = Form(
    mapping(
      "name" -> nonEmptyText,
      "age" -> number(min = 1, max = 99)
    )(UserForm.apply)(UserForm.unapply)
  )
}