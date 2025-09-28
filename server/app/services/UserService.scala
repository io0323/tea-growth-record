package services

import javax.inject.{Inject, Singleton}
import models.User
import repositories.UserRepository
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class UserService @Inject()(
  userRepository: UserRepository
)(implicit ec: ExecutionContext) {

  def findById(id: Long): Future[Option[User]] = {
    userRepository.findById(id)
  }

  def findByEmail(email: String): Future[Option[User]] = {
    userRepository.findByEmail(email)
  }

  def authenticate(email: String, password: String): Future[Option[User]] = {
    userRepository.findByEmail(email).map {
      case Some(user) if user.checkPassword(password) => Some(user)
      case _ => None
    }
  }

  def create(email: String, password: String, name: Option[String]): Future[Option[User]] = {
    val user = User(
      email = email,
      password = password, // TODO: パスワードのハッシュ化
      name = name
    )
    userRepository.create(user)
  }

  def update(user: User): Future[Option[User]] = {
    userRepository.update(user)
  }

  def updatePassword(id: Long, password: String): Future[Boolean] = {
    userRepository.updatePassword(id, password) // TODO: パスワードのハッシュ化
  }
}