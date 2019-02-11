package models

import org.joda.time.DateTime

case class User(id: Int, name: String, age: Int, deleted: Option[DateTime] = None)