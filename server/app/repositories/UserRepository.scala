package repositories

import javax.inject.{Inject, Singleton}
import models.User
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile
import scala.concurrent.{ExecutionContext, Future}

/**
 * ユーザーのデータアクセスを担当するリポジトリクラス
 */
@Singleton
class UserRepository @Inject()(
  protected val dbConfigProvider: DatabaseConfigProvider
)(implicit ec: ExecutionContext) extends HasDatabaseConfigProvider[JdbcProfile] {
  import profile.api._

  private val users = TableQuery[User.Users]

  /**
   * メールアドレスでユーザーを検索する
   */
  def findByEmail(email: String): Future[Option[User]] = {
    val query = users.filter(_.email === email)
    db.run(query.result.headOption)
  }

  /**
   * IDでユーザーを検索する
   */
  def findById(id: Long): Future[Option[User]] = {
    val query = users.filter(_.id === id)
    db.run(query.result.headOption)
  }

  def findByUsername(username: String): Future[Option[User]] = {
    db.run(users.filter(_.username === username).result.headOption)
  }

  /**
   * ユーザーを作成する
   */
  def create(user: User): Future[Option[User]] = {
    val query = users returning users.map(_.id) into ((user, id) => user.copy(id = Some(id)))
    db.run(query += user).map(Some(_))
  }

  /**
   * ユーザーを更新する
   */
  def update(user: User): Future[Option[User]] = {
    user.id match {
      case Some(id) =>
        val query = users.filter(_.id === id)
        db.run(query.update(user)).map {
          case 1 => Some(user)
          case _ => None
        }
      case None => Future.successful(None)
    }
  }

  /**
   * パスワードを更新する
   */
  def updatePassword(id: Long, password: String): Future[Boolean] = {
    val query = users.filter(_.id === id).map(_.password)
    db.run(query.update(password)).map(_ > 0)
  }

  /**
   * ユーザーを削除する
   */
  def delete(id: Long): Future[Boolean] = {
    db.run(users.filter(_.id === id).delete).map(_ > 0)
  }
}