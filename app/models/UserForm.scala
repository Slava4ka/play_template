package models

import play.api.data.Form
import play.api.data.Forms._

case class UserForm(id: Int, name: String, age: Int)


object UserForm {
  val form: Form[UserForm] = Form(
    mapping(
      "id" -> number,
      "name" -> nonEmptyText,
      "age" -> number(min = 1, max = 99)
    )(UserForm.apply)(UserForm.unapply)
  )
}