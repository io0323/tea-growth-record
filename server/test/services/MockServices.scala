package services

import models.{User, PasswordResetToken}
import scala.concurrent.{ExecutionContext, Future}

class MockUserService(implicit ec: ExecutionContext) extends UserService {
  private var users = Map[Long, User]()
  private var nextId = 1L

  override def create(username: String, password: String): Future[User] = Future {
    val user = User(Some(nextId), username, password)
    users += (nextId -> user)
    nextId += 1
    user
  }

  override def findByUsername(username: String): Future[Option[User]] = Future {
    users.values.find(_.username == username)
  }

  override def findById(id: Long): Future[Option[User]] = Future {
    users.get(id)
  }

  override def updatePassword(id: Long, newPassword: String): Future[Int] = Future {
    users.get(id).map { user =>
      users += (id -> user.copy(password = newPassword))
      1
    }.getOrElse(0)
  }
}

class MockPasswordResetService(implicit ec: ExecutionContext) extends PasswordResetService {
  private var tokens = Map[String, PasswordResetToken]()
  private var nextId = 1L

  override def createToken(userId: Long): Future[PasswordResetToken] = Future {
    val token = PasswordResetToken(
      Some(nextId),
      userId,
      java.util.UUID.randomUUID().toString,
      System.currentTimeMillis() + 86400000
    )
    tokens += (token.token -> token)
    nextId += 1
    token
  }

  override def findUserByToken(token: String): Future[Option[User]] = Future {
    tokens.get(token).flatMap { resetToken =>
      if (resetToken.expiresAt > System.currentTimeMillis()) {
        Some(User(Some(resetToken.userId), "test@example.com", "password"))
      } else {
        None
      }
    }
  }

  override def deleteToken(token: String): Future[Int] = Future {
    if (tokens.contains(token)) {
      tokens -= token
      1
    } else {
      0
    }
  }

  override def updatePassword(userId: Long, newPassword: String): Future[Int] = Future {
    1
  }
}

class MockEmailService(implicit ec: ExecutionContext) extends EmailService {
  private var sentEmails = List[String]()

  override def sendPasswordResetEmail(username: String, token: String): Future[String] = Future {
    val email = s"Reset email sent to $username with token $token"
    sentEmails = email :: sentEmails
    email
  }

  def getSentEmails: List[String] = sentEmails
} 