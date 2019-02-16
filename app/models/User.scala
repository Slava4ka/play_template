package models

import org.joda.time.DateTime
import play.api.libs.json.{Json, Reads, Writes, __}
import play.api.libs.functional.syntax._

case class User(
  id: Int = 0,
  name: String,
  age: Int,
  deleted: Option[DateTime] = None
)

trait UserJson {
  val writes: Writes[User] = (user: User) => {
    Json.obj(
      "name" -> user.name,
      "age" -> user.age
    )
  }

  val reads: Reads[User] = (
    Reads.pure(0) and
    (__ \ "name").read[String] and
    (__ \ "age").read[Int] and
    Reads.pure(None)
  ) (User.apply _) //все что получил передать в applay и собери объект

}

object User extends UserJson