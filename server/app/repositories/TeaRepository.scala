package repositories

import javax.inject.{Inject, Singleton}
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile
import models.{Tea, TeaStatusType}
import scala.concurrent.{ExecutionContext, Future}

/**
 * お茶のリポジトリ
 */
@Singleton
class TeaRepository @Inject()(
  protected val dbConfigProvider: DatabaseConfigProvider
)(implicit ec: ExecutionContext) extends HasDatabaseConfigProvider[JdbcProfile] {

  import profile.api._

  private val teas = TableQuery[Tea.Teas]

  /**
   * ユーザーのお茶一覧を取得する
   */
  def findByUserId(userId: Long): Future[Seq[Tea]] = {
    val query = teas.filter(_.userId === userId)
    db.run(query.result)
  }

  /**
   * IDでお茶を検索する
   */
  def findById(id: Long): Future[Option[Tea]] = {
    val query = teas.filter(_.id === id)
    db.run(query.result.headOption)
  }

  /**
   * 新しいお茶を登録する
   */
  def create(tea: Tea): Future[Option[Tea]] = {
    val query = teas returning teas.map(_.id) into ((tea, id) => tea.copy(id = Some(id)))
    db.run(query += tea).map(Some(_))
  }

  /**
   * お茶の情報を更新する
   */
  def update(tea: Tea): Future[Option[Tea]] = {
    tea.id match {
      case Some(id) =>
        val query = teas.filter(_.id === id)
        db.run(query.update(tea)).map {
          case 1 => Some(tea)
          case _ => None
        }
      case None => Future.successful(None)
    }
  }

  /**
   * お茶を削除する
   */
  def delete(id: Long): Future[Boolean] = {
    val query = teas.filter(_.id === id)
    db.run(query.delete).map(_ > 0)
  }

  /**
   * お茶の状態を更新する
   */
  def updateStatus(id: Long, status: TeaStatusType.TeaStatusType): Future[Boolean] = {
    val query = teas.filter(_.id === id).map(_.status)
    db.run(query.update(status)).map(_ > 0)
  }

  /**
   * お茶の数量を更新する
   */
  def updateQuantity(id: Long, quantity: BigDecimal): Future[Boolean] = {
    val query = teas.filter(_.id === id).map(_.quantity)
    db.run(query.update(Some(quantity))).map(_ > 0)
  }
} 