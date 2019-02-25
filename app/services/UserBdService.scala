package services

import scala.concurrent.Future

import com.google.inject.Inject
import daos.UserBdDAO
import models.User

class UserBdService @Inject()(userBdDAO: UserBdDAO) {

  def findAll: Future[Seq[User]] = userBdDAO.findAll // найти всех
}