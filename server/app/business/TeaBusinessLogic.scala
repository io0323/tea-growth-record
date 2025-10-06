package business

import models._
import services._
import utils.ValidationUtils
import scala.concurrent.{ExecutionContext, Future}
import scala.math.BigDecimal
import java.time.{LocalDateTime, Period}
import javax.inject.{Inject, Singleton}

/**
 * お茶管理の高度なビジネスロジック
 */
@Singleton
class TeaBusinessLogic @Inject()(
  teaService: TeaService,
  userService: UserService
)(implicit ec: ExecutionContext) {

  /**
   * お茶の在庫管理
   */
  case class InventoryStatus(
    totalItems: Int,
    inStockItems: Int,
    outOfStockItems: Int,
    orderedItems: Int,
    archivedItems: Int,
    lowStockItems: Int,
    totalValue: BigDecimal,
    averagePrice: BigDecimal
  )

  /**
   * お茶の品質評価
   */
  case class QualityAssessment(
    freshnessScore: Int,
    storageScore: Int,
    overallScore: Int,
    recommendations: List[String]
  )

  /**
   * お茶の購入推奨
   */
  case class PurchaseRecommendation(
    teaType: String,
    priority: String,
    reason: String,
    suggestedQuantity: BigDecimal,
    estimatedCost: BigDecimal
  )

  /**
   * 在庫状況の分析
   */
  def analyzeInventory(userId: Long): Future[InventoryStatus] = {
    teaService.findByUserId(userId).map { teas =>
      val totalItems = teas.length
      val inStockItems = teas.count(_.status == TeaStatusType.InStock)
      val outOfStockItems = teas.count(_.status == TeaStatusType.OutOfStock)
      val orderedItems = teas.count(_.status == TeaStatusType.Ordered)
      val archivedItems = teas.count(_.status == TeaStatusType.Archived)
      
      val lowStockItems = teas.count { tea =>
        tea.status == TeaStatusType.InStock && 
        tea.quantity.exists(_ < 50) // 50g未満を低在庫とする
      }
      
      val totalValue = teas.flatMap(_.price).sum
      val averagePrice = if (teas.nonEmpty) totalValue / teas.length else BigDecimal(0)
      
      InventoryStatus(
        totalItems = totalItems,
        inStockItems = inStockItems,
        outOfStockItems = outOfStockItems,
        orderedItems = orderedItems,
        archivedItems = archivedItems,
        lowStockItems = lowStockItems,
        totalValue = totalValue,
        averagePrice = averagePrice
      )
    }
  }

  /**
   * お茶の品質評価
   */
  def assessTeaQuality(tea: Tea): QualityAssessment = {
    val freshnessScore = calculateFreshnessScore(tea)
    val storageScore = calculateStorageScore(tea)
    val overallScore = (freshnessScore + storageScore) / 2
    
    val recommendations = generateRecommendations(tea, freshnessScore, storageScore)
    
    QualityAssessment(
      freshnessScore = freshnessScore,
      storageScore = storageScore,
      overallScore = overallScore,
      recommendations = recommendations
    )
  }

  /**
   * 鮮度スコアの計算
   */
  private def calculateFreshnessScore(tea: Tea): Int = {
    val daysSincePurchase = Period.between(
      tea.purchaseDate.toLocalDate,
      LocalDateTime.now().toLocalDate
    ).getDays
    
    daysSincePurchase match {
      case d if d <= 30 => 100
      case d if d <= 90 => 80
      case d if d <= 180 => 60
      case d if d <= 365 => 40
      case _ => 20
    }
  }

  /**
   * 保存スコアの計算
   */
  private def calculateStorageScore(tea: Tea): Int = {
    var score = 50 // ベーススコア
    
    // 説明文の長さで保存状況を推測
    tea.description match {
      case Some(desc) if desc.length > 50 => score += 20
      case Some(desc) if desc.length > 20 => score += 10
      case _ => score += 0
    }
    
    // 価格で品質を推測
    tea.price match {
      case Some(price) if price > 5000 => score += 20
      case Some(price) if price > 2000 => score += 10
      case _ => score += 0
    }
    
    Math.min(score, 100)
  }

  /**
   * 推奨事項の生成
   */
  private def generateRecommendations(tea: Tea, freshnessScore: Int, storageScore: Int): List[String] = {
    var recommendations = List.empty[String]
    
    if (freshnessScore < 60) {
      recommendations = recommendations :+ "このお茶は古くなっている可能性があります。早めに消費することをお勧めします。"
    }
    
    if (storageScore < 60) {
      recommendations = recommendations :+ "保存方法を見直すことをお勧めします。密閉容器での保存を検討してください。"
    }
    
    if (tea.quantity.exists(_ < 50)) {
      recommendations = recommendations :+ "在庫が少なくなっています。補充を検討してください。"
    }
    
    if (recommendations.isEmpty) {
      recommendations = List("良好な状態です。現在の保存方法を続けてください。")
    }
    
    recommendations
  }

  /**
   * 購入推奨の生成
   */
  def generatePurchaseRecommendations(userId: Long): Future[List[PurchaseRecommendation]] = {
    teaService.findByUserId(userId).map { teas =>
      val recommendations = scala.collection.mutable.ListBuffer[PurchaseRecommendation]()
      
      // 在庫切れのお茶
      val outOfStockTeas = teas.filter(_.status == TeaStatusType.OutOfStock)
      outOfStockTeas.foreach { tea =>
        recommendations += PurchaseRecommendation(
          teaType = tea.name,
          priority = "高",
          reason = "在庫切れのため補充が必要",
          suggestedQuantity = tea.quantity.getOrElse(100),
          estimatedCost = tea.price.getOrElse(2000)
        )
      }
      
      // 低在庫のお茶
      val lowStockTeas = teas.filter { tea =>
        tea.status == TeaStatusType.InStock && 
        tea.quantity.exists(_ < 50)
      }
      lowStockTeas.foreach { tea =>
        recommendations += PurchaseRecommendation(
          teaType = tea.name,
          priority = "中",
          reason = "在庫が少なくなっています",
          suggestedQuantity = 100,
          estimatedCost = tea.price.getOrElse(2000)
        )
      }
      
      // 人気のお茶タイプの推奨
      val popularTypes = teas.groupBy(_.typeId).mapValues(_.length).toList.sortBy(-_._2)
      if (popularTypes.nonEmpty) {
        val mostPopularType = popularTypes.head._1
        recommendations += PurchaseRecommendation(
          teaType = s"人気のお茶タイプ (ID: $mostPopularType)",
          priority = "低",
          reason = "よく飲まれているお茶タイプです",
          suggestedQuantity = 100,
          estimatedCost = 1500
        )
      }
      
      recommendations.toList
    }
  }

  /**
   * お茶の消費予測
   */
  def predictTeaConsumption(userId: Long): Future[Map[Long, BigDecimal]] = {
    teaService.findByUserId(userId).map { teas =>
      teas.map { tea =>
        val consumptionRate = calculateConsumptionRate(tea)
        val predictedConsumption = tea.quantity.getOrElse(0) * consumptionRate
        tea.id.getOrElse(0L) -> predictedConsumption
      }.toMap
    }
  }

  /**
   * 消費率の計算
   */
  private def calculateConsumptionRate(tea: Tea): BigDecimal = {
    val daysSincePurchase = Period.between(
      tea.purchaseDate.toLocalDate,
      LocalDateTime.now().toLocalDate
    ).getDays
    
    if (daysSincePurchase == 0) BigDecimal(0.1) // 新規購入
    else {
      val remainingQuantity = tea.quantity.getOrElse(0)
      val consumedQuantity = tea.quantity.getOrElse(0) * 0.8 // 仮定: 80%消費
      consumedQuantity / daysSincePurchase
    }
  }

  /**
   * お茶の最適化提案
   */
  def optimizeTeaCollection(userId: Long): Future[List[String]] = {
    for {
      inventory <- analyzeInventory(userId)
      recommendations <- generatePurchaseRecommendations(userId)
    } yield {
      var suggestions = List.empty[String]
      
      if (inventory.lowStockItems > 0) {
        suggestions = suggestions :+ s"${inventory.lowStockItems}種類のお茶が低在庫です。補充を検討してください。"
      }
      
      if (inventory.outOfStockItems > 0) {
        suggestions = suggestions :+ s"${inventory.outOfStockItems}種類のお茶が在庫切れです。緊急で補充が必要です。"
      }
      
      if (inventory.totalValue > 50000) {
        suggestions = suggestions :+ "お茶の在庫価値が高くなっています。消費を促進することをお勧めします。"
      }
      
      if (inventory.averagePrice < 1000) {
        suggestions = suggestions :+ "平均価格が低めです。より高品質なお茶の購入を検討してください。"
      }
      
      if (suggestions.isEmpty) {
        suggestions = List("お茶のコレクションは良好な状態です。現在の管理方法を続けてください。")
      }
      
      suggestions
    }
  }

  /**
   * お茶の統計情報
   */
  def getTeaStatistics(userId: Long): Future[Map[String, Any]] = {
    teaService.findByUserId(userId).map { teas =>
      val typeDistribution = teas.groupBy(_.typeId).mapValues(_.length)
      val statusDistribution = teas.groupBy(_.status).mapValues(_.length)
      val priceRange = teas.flatMap(_.price)
      val quantityRange = teas.flatMap(_.quantity)
      
      Map(
        "totalTeas" -> teas.length,
        "typeDistribution" -> typeDistribution,
        "statusDistribution" -> statusDistribution,
        "averagePrice" -> (if (priceRange.nonEmpty) priceRange.sum / priceRange.length else 0),
        "totalValue" -> priceRange.sum,
        "averageQuantity" -> (if (quantityRange.nonEmpty) quantityRange.sum / quantityRange.length else 0),
        "oldestTea" -> teas.minByOption(_.purchaseDate).map(_.name),
        "newestTea" -> teas.maxByOption(_.purchaseDate).map(_.name)
      )
    }
  }
}
