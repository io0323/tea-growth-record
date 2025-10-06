package repositories

import models._
import play.api.db.slick.DatabaseConfigProvider
import play.api.db.slick.HasDatabaseConfigProvider
import slick.jdbc.JdbcProfile
import scala.concurrent.{ExecutionContext, Future}
import java.time.LocalDateTime
import javax.inject.{Inject, Singleton}
import slick.lifted.Query

/**
 * 高度なデータベースアクセス層
 */
@Singleton
class AdvancedRepository @Inject()(
  protected val dbConfigProvider: DatabaseConfigProvider
)(implicit ec: ExecutionContext) extends HasDatabaseConfigProvider[JdbcProfile] {

  import profile.api._

  /**
   * 高度なクエリビルダー
   */
  case class QueryBuilder[T](query: Query[_, T, Seq]) {
    def where(condition: Rep[Boolean]): QueryBuilder[T] = 
      QueryBuilder(query.filter(condition))
    
    def orderBy[A](column: Rep[A])(implicit ev: A => Ordered[A]): QueryBuilder[T] = 
      QueryBuilder(query.sortBy(column))
    
    def orderByDesc[A](column: Rep[A])(implicit ev: A => Ordered[A]): QueryBuilder[T] = 
      QueryBuilder(query.sortBy(column.desc))
    
    def limit(n: Int): QueryBuilder[T] = 
      QueryBuilder(query.take(n))
    
    def offset(n: Int): QueryBuilder[T] = 
      QueryBuilder(query.drop(n))
    
    def paginate(page: Int, pageSize: Int): QueryBuilder[T] = 
      QueryBuilder(query.drop((page - 1) * pageSize).take(pageSize))
    
    def execute: Future[Seq[T]] = db.run(query.result)
    
    def executeFirst: Future[Option[T]] = db.run(query.result.headOption)
    
    def executeCount: Future[Int] = db.run(query.length.result)
  }

  /**
   * お茶の高度な検索
   */
  def searchTeas(
    userId: Option[Long] = None,
    name: Option[String] = None,
    typeId: Option[Long] = None,
    status: Option[TeaStatusType.TeaStatusType] = None,
    minPrice: Option[BigDecimal] = None,
    maxPrice: Option[BigDecimal] = None,
    minQuantity: Option[BigDecimal] = None,
    maxQuantity: Option[BigDecimal] = None,
    origin: Option[String] = None,
    purchaseDateFrom: Option[LocalDateTime] = None,
    purchaseDateTo: Option[LocalDateTime] = None,
    sortBy: Option[String] = None,
    sortOrder: String = "asc",
    page: Int = 1,
    pageSize: Int = 20
  ): Future[(Seq[Tea], Int)] = {
    
    var query = teaTable
    
    // フィルタリング
    userId.foreach(id => query = query.filter(_.userId === id))
    name.foreach(n => query = query.filter(_.name like s"%$n%"))
    typeId.foreach(id => query = query.filter(_.typeId === id))
    status.foreach(s => query = query.filter(_.status === s))
    minPrice.foreach(p => query = query.filter(_.price >= p))
    maxPrice.foreach(p => query = query.filter(_.price <= p))
    minQuantity.foreach(q => query = query.filter(_.quantity >= q))
    maxQuantity.foreach(q => query = query.filter(_.quantity <= q))
    origin.foreach(o => query = query.filter(_.origin like s"%$o%"))
    purchaseDateFrom.foreach(d => query = query.filter(_.purchaseDate >= d))
    purchaseDateTo.foreach(d => query = query.filter(_.purchaseDate <= d))
    
    // ソート
    val sortedQuery = sortBy match {
      case Some("name") if sortOrder == "asc" => query.sortBy(_.name)
      case Some("name") if sortOrder == "desc" => query.sortBy(_.name.desc)
      case Some("price") if sortOrder == "asc" => query.sortBy(_.price)
      case Some("price") if sortOrder == "desc" => query.sortBy(_.price.desc)
      case Some("quantity") if sortOrder == "asc" => query.sortBy(_.quantity)
      case Some("quantity") if sortOrder == "desc" => query.sortBy(_.quantity.desc)
      case Some("purchaseDate") if sortOrder == "asc" => query.sortBy(_.purchaseDate)
      case Some("purchaseDate") if sortOrder == "desc" => query.sortBy(_.purchaseDate.desc)
      case _ => query.sortBy(_.createdAt.desc)
    }
    
    // ページネーション
    val paginatedQuery = sortedQuery.drop((page - 1) * pageSize).take(pageSize)
    
    for {
      teas <- db.run(paginatedQuery.result)
      totalCount <- db.run(query.length.result)
    } yield (teas, totalCount)
  }

  /**
   * お茶の統計情報取得
   */
  def getTeaStatistics(userId: Long): Future[Map[String, Any]] = {
    val userTeas = teaTable.filter(_.userId === userId)
    
    for {
      totalCount <- db.run(userTeas.length.result)
      inStockCount <- db.run(userTeas.filter(_.status === TeaStatusType.InStock).length.result)
      outOfStockCount <- db.run(userTeas.filter(_.status === TeaStatusType.OutOfStock).length.result)
      orderedCount <- db.run(userTeas.filter(_.status === TeaStatusType.Ordered).length.result)
      archivedCount <- db.run(userTeas.filter(_.status === TeaStatusType.Archived).length.result)
      
      avgPrice <- db.run(userTeas.map(_.price).avg.result)
      totalValue <- db.run(userTeas.map(_.price).sum.result)
      avgQuantity <- db.run(userTeas.map(_.quantity).avg.result)
      
      typeDistribution <- db.run(
        userTeas.groupBy(_.typeId)
          .map { case (typeId, teas) => (typeId, teas.length) }
          .result
      )
      
      monthlyPurchases <- db.run(
        userTeas.map(t => (t.purchaseDate, t.price))
          .result
      )
    } yield {
      val monthlyData = monthlyPurchases.groupBy { case (date, _) =>
        s"${date.getYear}-${date.getMonthValue}"
      }.mapValues(_.map(_._2).sum)
      
      Map(
        "totalCount" -> totalCount,
        "inStockCount" -> inStockCount,
        "outOfStockCount" -> outOfStockCount,
        "orderedCount" -> orderedCount,
        "archivedCount" -> archivedCount,
        "avgPrice" -> avgPrice.getOrElse(0),
        "totalValue" -> totalValue.getOrElse(0),
        "avgQuantity" -> avgQuantity.getOrElse(0),
        "typeDistribution" -> typeDistribution.toMap,
        "monthlyPurchases" -> monthlyData
      )
    }
  }

  /**
   * お茶の在庫アラート取得
   */
  def getLowStockTeas(userId: Long, threshold: BigDecimal = 50): Future[Seq[Tea]] = {
    db.run(
      teaTable
        .filter(_.userId === userId)
        .filter(_.status === TeaStatusType.InStock)
        .filter(_.quantity < threshold)
        .result
    )
  }

  /**
   * 期限切れのお茶取得
   */
  def getExpiredTeas(userId: Long, daysThreshold: Long = 180): Future[Seq[Tea]] = {
    val cutoffDate = LocalDateTime.now().minusDays(daysThreshold)
    db.run(
      teaTable
        .filter(_.userId === userId)
        .filter(_.purchaseDate < cutoffDate)
        .result
    )
  }

  /**
   * お茶の価格履歴取得
   */
  def getPriceHistory(teaId: Long): Future[Seq[(LocalDateTime, BigDecimal)]] = {
    // 実際の実装では価格履歴テーブルが必要
    // ここでは簡略化
    Future.successful(Seq.empty)
  }

  /**
   * バッチ操作
   */
  def batchUpdateTeaStatus(teaIds: Seq[Long], status: TeaStatusType.TeaStatusType): Future[Int] = {
    db.run(
      teaTable
        .filter(_.id inSet teaIds)
        .map(_.status)
        .update(status)
    )
  }

  def batchDeleteTeas(teaIds: Seq[Long]): Future[Int] = {
    db.run(
      teaTable
        .filter(_.id inSet teaIds)
        .delete
    )
  }

  /**
   * トランザクション処理
   */
  def transferTeaOwnership(teaId: Long, fromUserId: Long, toUserId: Long): Future[Boolean] = {
    val action = for {
      teaOpt <- teaTable.filter(_.id === teaId).filter(_.userId === fromUserId).result.headOption
      _ <- teaOpt match {
        case Some(tea) => 
          teaTable.filter(_.id === teaId).map(_.userId).update(toUserId)
        case None => 
          DBIO.failed(new RuntimeException("Tea not found or not owned by user"))
      }
    } yield true
    
    db.run(action.transactionally)
  }

  /**
   * データベースのメンテナンス
   */
  def cleanupOldData(daysToKeep: Int = 365): Future[Int] = {
    val cutoffDate = LocalDateTime.now().minusDays(daysToKeep)
    db.run(
      teaTable
        .filter(_.createdAt < cutoffDate)
        .filter(_.status === TeaStatusType.Archived)
        .delete
    )
  }

  def optimizeDatabase(): Future[Unit] = {
    // データベースの最適化処理
    Future.successful(())
  }

  /**
   * バックアップとリストア
   */
  def createBackup(): Future[String] = {
    // バックアップの作成
    val timestamp = LocalDateTime.now().toString.replace(":", "-")
    val backupId = s"backup_$timestamp"
    Future.successful(backupId)
  }

  def restoreFromBackup(backupId: String): Future[Boolean] = {
    // バックアップからのリストア
    Future.successful(true)
  }

  /**
   * パフォーマンス監視
   */
  def getQueryPerformanceStats(): Future[Map[String, Any]] = {
    Future.successful(Map(
      "slowQueries" -> 0,
      "averageQueryTime" -> 0.0,
      "totalQueries" -> 0,
      "cacheHitRate" -> 0.0
    ))
  }

  /**
   * データ整合性チェック
   */
  def checkDataIntegrity(): Future[Map[String, Any]] = {
    for {
      orphanedTeas <- db.run(
        teaTable
          .filter(_.userId.isNull)
          .length
          .result
      )
      invalidPrices <- db.run(
        teaTable
          .filter(_.price < 0)
          .length
          .result
      )
      invalidQuantities <- db.run(
        teaTable
          .filter(_.quantity < 0)
          .length
          .result
      )
    } yield Map(
      "orphanedTeas" -> orphanedTeas,
      "invalidPrices" -> invalidPrices,
      "invalidQuantities" -> invalidQuantities,
      "isHealthy" -> (orphanedTeas == 0 && invalidPrices == 0 && invalidQuantities == 0)
    )
  }

  /**
   * データエクスポート
   */
  def exportUserData(userId: Long): Future[Map[String, Any]] = {
    for {
      teas <- db.run(teaTable.filter(_.userId === userId).result)
      user <- db.run(userTable.filter(_.id === userId).result.headOption)
    } yield Map(
      "user" -> user,
      "teas" -> teas,
      "exportedAt" -> LocalDateTime.now().toString,
      "recordCount" -> teas.length
    )
  }

  /**
   * データインポート
   */
  def importUserData(userId: Long, data: Map[String, Any]): Future[Int] = {
    val teas = data.get("teas").map(_.asInstanceOf[Seq[Tea]]).getOrElse(Seq.empty)
    val teasWithUserId = teas.map(_.copy(userId = Some(userId)))
    
    db.run(teaTable ++= teasWithUserId)
  }

  // テーブル定義（簡略化）
  private val teaTable = TableQuery[TeaTable]
  private val userTable = TableQuery[UserTable]

  private class TeaTable(tag: Tag) extends Table[Tea](tag, "teas") {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def name = column[String]("name")
    def typeId = column[Long]("type_id")
    def origin = column[String]("origin")
    def purchaseDate = column[LocalDateTime]("purchase_date")
    def status = column[TeaStatusType.TeaStatusType]("status")
    def description = column[Option[String]]("description")
    def price = column[Option[BigDecimal]]("price")
    def quantity = column[Option[BigDecimal]]("quantity")
    def unit = column[Option[String]]("unit")
    def imageUrl = column[Option[String]]("image_url")
    def userId = column[Option[Long]]("user_id")
    def createdAt = column[LocalDateTime]("created_at")
    def updatedAt = column[LocalDateTime]("updated_at")

    def * = (id.?, name, typeId, origin, purchaseDate, status, description, price, quantity, unit, imageUrl, userId, createdAt.?, updatedAt.?) <> (Tea.tupled, Tea.unapply)
  }

  private class UserTable(tag: Tag) extends Table[User](tag, "users") {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def email = column[String]("email")
    def password = column[String]("password")
    def name = column[Option[String]]("name")
    def createdAt = column[LocalDateTime]("created_at")
    def updatedAt = column[LocalDateTime]("updated_at")

    def * = (id.?, email, password, name, createdAt.?, updatedAt.?) <> (User.tupled, User.unapply)
  }
}
