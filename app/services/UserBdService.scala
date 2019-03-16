package services

import scala.concurrent.Future

import com.google.inject.Inject
import daos.UserBdDAO
import models.User

class UserBdService @Inject()(userBdDAO: UserBdDAO) {

  def findAll: Future[Seq[User]] = userBdDAO.findAll // найти всех

  def addUser(user: User): Future[String] = userBdDAO.addUser(user) // добавить пользователя

  def editUser(user: User, id: Int): Future[Int] = {
    val userCopy = user.copy(id = id)
    println("userCopy: " + userCopy)
    userBdDAO.editUser(userCopy)
  } // добавить или отредактировать пользователя

  def findActiveUsers: Future[Seq[User]] = userBdDAO.findActiveUsers // найти не удаленных пользователей

  def findDeletedUsers: Future[Seq[User]] = userBdDAO.findDeletedUsers // найти удаленных пользователей

  def removeUser(id: Int): Future[Int] = userBdDAO.removeUser(id) // удалить пользователя

  def restoreUser(id: Int): Future[Int] = userBdDAO.restoreUser(id) // восстановить пользователя

  def findUserByID(id: Int): Future[Option[User]] = userBdDAO.findUserByID(id) // найти пользователя по id
}