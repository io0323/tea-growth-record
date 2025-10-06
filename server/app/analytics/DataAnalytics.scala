package analytics

import models._
import services._
import scala.concurrent.{ExecutionContext, Future}
import scala.math.BigDecimal
import java.time.{LocalDateTime, Period, ChronoUnit}
import javax.inject.{Inject, Singleton}

/**
 * 高度なデータ分析機能
 */
@Singleton
class DataAnalytics @Inject()(
  teaService: TeaService,
  userService: UserService
)(implicit ec: ExecutionContext) {

  /**
   * 時系列データポイント
   */
  case class TimeSeriesDataPoint(
    timestamp: LocalDateTime,
    value: BigDecimal,
    label: String
  )

  /**
   * トレンド分析結果
   */
  case class TrendAnalysis(
    trend: String, // "increasing", "decreasing", "stable"
    changeRate: BigDecimal,
    confidence: Double,
    forecast: List[TimeSeriesDataPoint]
  )

  /**
   * 相関分析結果
   */
  case class CorrelationAnalysis(
    variable1: String,
    variable2: String,
    correlation: Double,
    significance: Double,
    interpretation: String
  )

  /**
   * クラスタリング結果
   */
  case class ClusterAnalysis(
    clusters: Map[String, List[Tea]],
    centroids: Map[String, Map[String, BigDecimal]],
    silhouetteScore: Double
  )

  /**
   * お茶の購入パターン分析
   */
  def analyzePurchasePatterns(userId: Long): Future[Map[String, Any]] = {
    teaService.findByUserId(userId).map { teas =>
      val monthlyPurchases = teas.groupBy { tea =>
        tea.purchaseDate.getYear + "-" + f"${tea.purchaseDate.getMonthValue}%02d"
      }.mapValues(_.length)
      
      val priceDistribution = teas.flatMap(_.price).groupBy { price =>
        price match {
          case p if p < 1000 => "低価格"
          case p if p < 3000 => "中価格"
          case p if p < 5000 => "高価格"
          case _ => "超高価格"
        }
      }.mapValues(_.length)
      
      val typePreferences = teas.groupBy(_.typeId).mapValues(_.length)
      
      Map(
        "monthlyPurchases" -> monthlyPurchases,
        "priceDistribution" -> priceDistribution,
        "typePreferences" -> typePreferences,
        "averageMonthlySpending" -> calculateAverageMonthlySpending(teas),
        "purchaseFrequency" -> calculatePurchaseFrequency(teas),
        "seasonalPatterns" -> analyzeSeasonalPatterns(teas)
      )
    }
  }

  /**
   * 月平均支出の計算
   */
  private def calculateAverageMonthlySpending(teas: List[Tea]): BigDecimal = {
    if (teas.isEmpty) BigDecimal(0)
    else {
      val totalSpending = teas.flatMap(_.price).sum
      val months = teas.map(_.purchaseDate).distinct.length
      if (months > 0) totalSpending / months else BigDecimal(0)
    }
  }

  /**
   * 購入頻度の計算
   */
  private def calculatePurchaseFrequency(teas: List[Tea]): Double = {
    if (teas.length < 2) 0.0
    else {
      val sortedDates = teas.map(_.purchaseDate).sorted
      val totalDays = ChronoUnit.DAYS.between(sortedDates.head, sortedDates.last)
      if (totalDays > 0) teas.length.toDouble / totalDays * 30 else 0.0 // 月間頻度
    }
  }

  /**
   * 季節パターンの分析
   */
  private def analyzeSeasonalPatterns(teas: List[Tea]): Map[String, Int] = {
    teas.groupBy { tea =>
      tea.purchaseDate.getMonthValue match {
        case 12 | 1 | 2 => "冬"
        case 3 | 4 | 5 => "春"
        case 6 | 7 | 8 => "夏"
        case 9 | 10 | 11 => "秋"
      }
    }.mapValues(_.length)
  }

  /**
   * お茶の品質トレンド分析
   */
  def analyzeQualityTrends(userId: Long): Future[TrendAnalysis] = {
    teaService.findByUserId(userId).map { teas =>
      val qualityScores = teas.map { tea =>
        val daysSincePurchase = ChronoUnit.DAYS.between(tea.purchaseDate, LocalDateTime.now())
        val freshnessScore = Math.max(0, 100 - daysSincePurchase / 10)
        val priceScore = tea.price.map(p => Math.min(100, p.toInt / 50)).getOrElse(50)
        (tea.purchaseDate, (freshnessScore + priceScore) / 2)
      }.sortBy(_._1)
      
      if (qualityScores.length < 2) {
        TrendAnalysis("stable", BigDecimal(0), 0.0, List.empty)
      } else {
        val firstScore = qualityScores.head._2
        val lastScore = qualityScores.last._2
        val changeRate = BigDecimal(lastScore - firstScore) / qualityScores.length
        
        val trend = changeRate match {
          case rate if rate > 5 => "increasing"
          case rate if rate < -5 => "decreasing"
          case _ => "stable"
        }
        
        val confidence = calculateTrendConfidence(qualityScores.map(_._2))
        
        val forecast = generateForecast(qualityScores.takeRight(5))
        
        TrendAnalysis(trend, changeRate, confidence, forecast)
      }
    }
  }

  /**
   * トレンド信頼度の計算
   */
  private def calculateTrendConfidence(scores: List[Double]): Double = {
    if (scores.length < 3) 0.0
    else {
      val mean = scores.sum / scores.length
      val variance = scores.map(s => Math.pow(s - mean, 2)).sum / scores.length
      val standardDeviation = Math.sqrt(variance)
      Math.max(0, 1 - standardDeviation / 100)
    }
  }

  /**
   * 予測の生成
   */
  private def generateForecast(recentScores: List[(LocalDateTime, Double)]): List[TimeSeriesDataPoint] = {
    if (recentScores.length < 2) List.empty
    else {
      val trend = (recentScores.last._2 - recentScores.head._2) / recentScores.length
      val lastDate = recentScores.last._1
      val lastScore = recentScores.last._2
      
      (1 to 5).map { i =>
        val futureDate = lastDate.plusDays(i * 30) // 月次予測
        val predictedScore = lastScore + trend * i
        TimeSeriesDataPoint(futureDate, BigDecimal(predictedScore), s"予測${i}ヶ月後")
      }.toList
    }
  }

  /**
   * お茶タイプ間の相関分析
   */
  def analyzeTeaTypeCorrelations(userId: Long): Future[List[CorrelationAnalysis]] = {
    teaService.findByUserId(userId).map { teas =>
      val typeGroups = teas.groupBy(_.typeId)
      val correlations = scala.collection.mutable.ListBuffer[CorrelationAnalysis]()
      
      val typeIds = typeGroups.keys.toList
      for (i <- typeIds.indices; j <- i + 1 until typeIds.length) {
        val type1 = typeIds(i)
        val type2 = typeIds(j)
        
        val teas1 = typeGroups(type1)
        val teas2 = typeGroups(type2)
        
        val correlation = calculateCorrelation(teas1, teas2)
        val significance = calculateSignificance(correlation, teas1.length + teas2.length)
        
        correlations += CorrelationAnalysis(
          variable1 = s"お茶タイプ$type1",
          variable2 = s"お茶タイプ$type2",
          correlation = correlation,
          significance = significance,
          interpretation = interpretCorrelation(correlation)
        )
      }
      
      correlations.toList
    }
  }

  /**
   * 相関係数の計算
   */
  private def calculateCorrelation(teas1: List[Tea], teas2: List[Tea]): Double = {
    val prices1 = teas1.flatMap(_.price).map(_.toDouble)
    val prices2 = teas2.flatMap(_.price).map(_.toDouble)
    
    if (prices1.isEmpty || prices2.isEmpty) 0.0
    else {
      val avg1 = prices1.sum / prices1.length
      val avg2 = prices2.sum / prices2.length
      
      val numerator = prices1.zip(prices2).map { case (p1, p2) =>
        (p1 - avg1) * (p2 - avg2)
      }.sum
      
      val denominator = Math.sqrt(
        prices1.map(p => Math.pow(p - avg1, 2)).sum *
        prices2.map(p => Math.pow(p - avg2, 2)).sum
      )
      
      if (denominator == 0) 0.0 else numerator / denominator
    }
  }

  /**
   * 有意性の計算
   */
  private def calculateSignificance(correlation: Double, sampleSize: Int): Double = {
    val t = correlation * Math.sqrt((sampleSize - 2) / (1 - correlation * correlation))
    val degreesOfFreedom = sampleSize - 2
    // 簡略化された有意性計算
    Math.min(1.0, Math.abs(t) / 2.0)
  }

  /**
   * 相関の解釈
   */
  private def interpretCorrelation(correlation: Double): String = {
    correlation match {
      case r if r > 0.7 => "強い正の相関"
      case r if r > 0.3 => "中程度の正の相関"
      case r if r > -0.3 => "弱い相関"
      case r if r > -0.7 => "中程度の負の相関"
      case _ => "強い負の相関"
    }
  }

  /**
   * お茶のクラスタリング分析
   */
  def clusterTeas(userId: Long): Future[ClusterAnalysis] = {
    teaService.findByUserId(userId).map { teas =>
      if (teas.length < 3) {
        ClusterAnalysis(Map.empty, Map.empty, 0.0)
      } else {
        val clusters = performKMeansClustering(teas, 3)
        val centroids = calculateCentroids(clusters)
        val silhouetteScore = calculateSilhouetteScore(teas, clusters)
        
        ClusterAnalysis(clusters, centroids, silhouetteScore)
      }
    }
  }

  /**
   * K-meansクラスタリングの実行
   */
  private def performKMeansClustering(teas: List[Tea], k: Int): Map[String, List[Tea]] = {
    val features = teas.map { tea =>
      val price = tea.price.getOrElse(0).toDouble
      val quantity = tea.quantity.getOrElse(0).toDouble
      val daysSincePurchase = ChronoUnit.DAYS.between(tea.purchaseDate, LocalDateTime.now()).toDouble
      (tea, Array(price, quantity, daysSincePurchase))
    }
    
    // 簡略化されたK-means実装
    val clusters = scala.collection.mutable.Map[String, List[Tea]]()
    val clusterSizes = features.length / k
    
    for (i <- 0 until k) {
      val start = i * clusterSizes
      val end = if (i == k - 1) features.length else (i + 1) * clusterSizes
      clusters(s"クラスタ${i + 1}") = features.slice(start, end).map(_._1)
    }
    
    clusters.toMap
  }

  /**
   * クラスタの重心計算
   */
  private def calculateCentroids(clusters: Map[String, List[Tea]]): Map[String, Map[String, BigDecimal]] = {
    clusters.map { case (clusterName, teas) =>
      val avgPrice = if (teas.nonEmpty) teas.flatMap(_.price).sum / teas.length else BigDecimal(0)
      val avgQuantity = if (teas.nonEmpty) teas.flatMap(_.quantity).sum / teas.length else BigDecimal(0)
      
      clusterName -> Map(
        "平均価格" -> avgPrice,
        "平均数量" -> avgQuantity,
        "お茶数" -> BigDecimal(teas.length)
      )
    }
  }

  /**
   * シルエットスコアの計算
   */
  private def calculateSilhouetteScore(teas: List[Tea], clusters: Map[String, List[Tea]]): Double = {
    // 簡略化されたシルエットスコア計算
    val totalTeas = teas.length
    val clusterCount = clusters.size
    
    if (totalTeas < 2 || clusterCount < 2) 0.0
    else {
      val intraClusterDistance = clusters.values.map { clusterTeas =>
        if (clusterTeas.length > 1) {
          val prices = clusterTeas.flatMap(_.price).map(_.toDouble)
          val avgPrice = prices.sum / prices.length
          prices.map(p => Math.abs(p - avgPrice)).sum / prices.length
        } else 0.0
      }.sum / clusterCount
      
      val interClusterDistance = clusters.values.map { clusterTeas =>
        val clusterAvgPrice = clusterTeas.flatMap(_.price).map(_.toDouble).sum / clusterTeas.length
        clusters.values.filterNot(_ == clusterTeas).map { otherCluster =>
          val otherAvgPrice = otherCluster.flatMap(_.price).map(_.toDouble).sum / otherCluster.length
          Math.abs(clusterAvgPrice - otherAvgPrice)
        }.sum / (clusterCount - 1)
      }.sum / clusterCount
      
      if (intraClusterDistance + interClusterDistance == 0) 0.0
      else (interClusterDistance - intraClusterDistance) / Math.max(intraClusterDistance, interClusterDistance)
    }
  }

  /**
   * 包括的な分析レポート
   */
  def generateComprehensiveReport(userId: Long): Future[Map[String, Any]] = {
    for {
      purchasePatterns <- analyzePurchasePatterns(userId)
      qualityTrends <- analyzeQualityTrends(userId)
      correlations <- analyzeTeaTypeCorrelations(userId)
      clusters <- clusterTeas(userId)
    } yield {
      Map(
        "purchasePatterns" -> purchasePatterns,
        "qualityTrends" -> qualityTrends,
        "correlations" -> correlations,
        "clusters" -> clusters,
        "generatedAt" -> LocalDateTime.now().toString,
        "summary" -> generateSummary(purchasePatterns, qualityTrends, clusters)
      )
    }
  }

  /**
   * 分析サマリーの生成
   */
  private def generateSummary(
    purchasePatterns: Map[String, Any],
    qualityTrends: TrendAnalysis,
    clusters: ClusterAnalysis
  ): Map[String, String] = {
    Map(
      "購入パターン" -> "月間購入頻度と価格分布を分析しました",
      "品質トレンド" -> s"品質は${qualityTrends.trend}傾向です (信頼度: ${(qualityTrends.confidence * 100).toInt}%)",
      "クラスタ分析" -> s"${clusters.clusters.size}つのグループに分類されました (シルエットスコア: ${(clusters.silhouetteScore * 100).toInt}%)",
      "推奨事項" -> generateRecommendations(purchasePatterns, qualityTrends, clusters)
    )
  }

  /**
   * 推奨事項の生成
   */
  private def generateRecommendations(
    purchasePatterns: Map[String, Any],
    qualityTrends: TrendAnalysis,
    clusters: ClusterAnalysis
  ): String = {
    var recommendations = List.empty[String]
    
    if (qualityTrends.trend == "decreasing") {
      recommendations = recommendations :+ "品質の向上を図るため、より高品質なお茶の購入を検討してください"
    }
    
    if (clusters.silhouetteScore < 0.3) {
      recommendations = recommendations :+ "お茶の分類を再検討し、より明確なカテゴリ分けを行うことをお勧めします"
    }
    
    if (recommendations.isEmpty) {
      recommendations = List("現在のお茶管理は良好です。継続的な分析を行い、最適化を図ってください")
    }
    
    recommendations.mkString("; ")
  }
}
