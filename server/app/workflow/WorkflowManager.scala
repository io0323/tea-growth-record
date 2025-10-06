package workflow

import scala.concurrent.{ExecutionContext, Future}
import java.time.{LocalDateTime, Duration}
import javax.inject.{Inject, Singleton}
import play.api.libs.json.{Json, JsValue}
import scala.util.{Try, Success, Failure}

/**
 * 高度なワークフロー管理システム
 */
@Singleton
class WorkflowManager @Inject()()(implicit ec: ExecutionContext) {

  /**
   * ワークフロー状態
   */
  sealed trait WorkflowStatus
  case object Pending extends WorkflowStatus
  case object Running extends WorkflowStatus
  case object Completed extends WorkflowStatus
  case object Failed extends WorkflowStatus
  case object Cancelled extends WorkflowStatus
  case object Paused extends WorkflowStatus

  /**
   * ワークフローステップ
   */
  case class WorkflowStep(
    id: String,
    name: String,
    stepType: String,
    config: Map[String, JsValue],
    dependencies: Set[String] = Set.empty,
    timeout: Option[Duration] = None,
    retryCount: Int = 0,
    maxRetries: Int = 3
  )

  /**
   * ワークフロー実行
   */
  case class WorkflowExecution(
    id: String,
    workflowId: String,
    status: WorkflowStatus,
    currentStep: Option[String],
    startedAt: LocalDateTime,
    completedAt: Option[LocalDateTime] = None,
    context: Map[String, JsValue] = Map.empty,
    error: Option[String] = None,
    retryCount: Int = 0
  )

  /**
   * ワークフロー定義
   */
  case class WorkflowDefinition(
    id: String,
    name: String,
    description: String,
    version: String,
    steps: List[WorkflowStep],
    triggers: List[WorkflowTrigger] = List.empty,
    timeout: Option[Duration] = None,
    retryPolicy: RetryPolicy = RetryPolicy.default
  )

  /**
   * ワークフロートリガー
   */
  sealed trait WorkflowTrigger
  case class ScheduleTrigger(cronExpression: String) extends WorkflowTrigger
  case class EventTrigger(eventType: String, conditions: Map[String, JsValue]) extends WorkflowTrigger
  case class ManualTrigger(description: String) extends WorkflowTrigger

  /**
   * リトライポリシー
   */
  case class RetryPolicy(
    maxRetries: Int = 3,
    retryDelay: Duration = Duration.ofMinutes(5),
    exponentialBackoff: Boolean = true,
    maxRetryDelay: Duration = Duration.ofHours(1)
  ) {
    def calculateDelay(attempt: Int): Duration = {
      if (!exponentialBackoff) {
        retryDelay
      } else {
        val delay = Duration.ofMillis(retryDelay.toMillis * Math.pow(2, attempt - 1).toLong)
        if (delay.compareTo(maxRetryDelay) > 0) maxRetryDelay else delay
      }
    }
  }

  object RetryPolicy {
    def default: RetryPolicy = RetryPolicy()
  }

  // メモリ内のワークフロー管理（本番環境ではデータベースを使用）
  private val workflowDefinitions = scala.collection.mutable.Map[String, WorkflowDefinition]()
  private val workflowExecutions = scala.collection.mutable.Map[String, WorkflowExecution]()
  private val stepExecutions = scala.collection.mutable.Map[String, StepExecution]()

  /**
   * ステップ実行
   */
  case class StepExecution(
    id: String,
    workflowExecutionId: String,
    stepId: String,
    status: WorkflowStatus,
    startedAt: LocalDateTime,
    completedAt: Option[LocalDateTime] = None,
    input: Map[String, JsValue] = Map.empty,
    output: Map[String, JsValue] = Map.empty,
    error: Option[String] = None,
    retryCount: Int = 0
  )

  /**
   * ワークフロー定義の登録
   */
  def registerWorkflow(definition: WorkflowDefinition): Future[String] = {
    workflowDefinitions(definition.id) = definition
    Future.successful(definition.id)
  }

  /**
   * ワークフロー実行の開始
   */
  def startWorkflow(workflowId: String, context: Map[String, JsValue] = Map.empty): Future[String] = {
    workflowDefinitions.get(workflowId) match {
      case Some(definition) =>
        val executionId = generateExecutionId()
        val execution = WorkflowExecution(
          id = executionId,
          workflowId = workflowId,
          status = Pending,
          currentStep = None,
          startedAt = LocalDateTime.now(),
          context = context
        )
        
        workflowExecutions(executionId) = execution
        
        // 非同期でワークフローを実行
        executeWorkflow(executionId)
        
        Future.successful(executionId)
      case None =>
        Future.failed(new IllegalArgumentException(s"Workflow $workflowId not found"))
    }
  }

  /**
   * ワークフローの実行
   */
  private def executeWorkflow(executionId: String): Unit = {
    workflowExecutions.get(executionId) match {
      case Some(execution) =>
        workflowDefinitions.get(execution.workflowId) match {
          case Some(definition) =>
            val updatedExecution = execution.copy(status = Running)
            workflowExecutions(executionId) = updatedExecution
            
            executeNextStep(executionId, definition)
          case None =>
            val failedExecution = execution.copy(
              status = Failed,
              error = Some("Workflow definition not found"),
              completedAt = Some(LocalDateTime.now())
            )
            workflowExecutions(executionId) = failedExecution
        }
      case None =>
        // 実行が見つからない場合の処理
    }
  }

  /**
   * 次のステップの実行
   */
  private def executeNextStep(executionId: String, definition: WorkflowDefinition): Unit = {
    val execution = workflowExecutions(executionId)
    val completedSteps = stepExecutions.values
      .filter(_.workflowExecutionId == executionId)
      .filter(_.status == Completed)
      .map(_.stepId)
      .toSet
    
    val nextStep = definition.steps.find { step =>
      step.dependencies.forall(completedSteps.contains) && 
      !completedSteps.contains(step.id)
    }
    
    nextStep match {
      case Some(step) =>
        executeStep(executionId, step)
      case None =>
        // すべてのステップが完了
        val completedExecution = execution.copy(
          status = Completed,
          completedAt = Some(LocalDateTime.now())
        )
        workflowExecutions(executionId) = completedExecution
    }
  }

  /**
   * ステップの実行
   */
  private def executeStep(executionId: String, step: WorkflowStep): Unit = {
    val stepExecutionId = generateStepExecutionId()
    val stepExecution = StepExecution(
      id = stepExecutionId,
      workflowExecutionId = executionId,
      stepId = step.id,
      status = Running,
      startedAt = LocalDateTime.now(),
      input = workflowExecutions(executionId).context
    )
    
    stepExecutions(stepExecutionId) = stepExecution
    
    val updatedExecution = workflowExecutions(executionId).copy(currentStep = Some(step.id))
    workflowExecutions(executionId) = updatedExecution
    
    // ステップの実行（非同期）
    executeStepLogic(stepExecutionId, step)
  }

  /**
   * ステップロジックの実行
   */
  private def executeStepLogic(stepExecutionId: String, step: WorkflowStep): Unit = {
    val stepExecution = stepExecutions(stepExecutionId)
    
    try {
      val result = step.stepType match {
        case "data_processing" => executeDataProcessingStep(step, stepExecution.input)
        case "notification" => executeNotificationStep(step, stepExecution.input)
        case "validation" => executeValidationStep(step, stepExecution.input)
        case "transformation" => executeTransformationStep(step, stepExecution.input)
        case "external_api" => executeExternalApiStep(step, stepExecution.input)
        case _ => throw new IllegalArgumentException(s"Unknown step type: ${step.stepType}")
      }
      
      val completedStepExecution = stepExecution.copy(
        status = Completed,
        completedAt = Some(LocalDateTime.now()),
        output = result
      )
      stepExecutions(stepExecutionId) = completedStepExecution
      
      // 次のステップを実行
      val execution = workflowExecutions(stepExecution.workflowExecutionId)
      workflowDefinitions.get(execution.workflowId).foreach(executeNextStep(stepExecution.workflowExecutionId, _))
      
    } catch {
      case e: Exception =>
        handleStepError(stepExecutionId, step, e)
    }
  }

  /**
   * ステップエラーの処理
   */
  private def handleStepError(stepExecutionId: String, step: WorkflowStep, error: Exception): Unit = {
    val stepExecution = stepExecutions(stepExecutionId)
    val retryCount = stepExecution.retryCount + 1
    
    if (retryCount <= step.maxRetries) {
      // リトライ
      val retryPolicy = workflowDefinitions(stepExecution.workflowExecutionId).retryPolicy
      val delay = retryPolicy.calculateDelay(retryCount)
      
      val retryStepExecution = stepExecution.copy(
        status = Pending,
        retryCount = retryCount,
        error = Some(error.getMessage)
      )
      stepExecutions(stepExecutionId) = retryStepExecution
      
      // 遅延後に再実行
      Future {
        Thread.sleep(delay.toMillis)
        executeStepLogic(stepExecutionId, step)
      }
    } else {
      // 最大リトライ回数に達した
      val failedStepExecution = stepExecution.copy(
        status = Failed,
        completedAt = Some(LocalDateTime.now()),
        error = Some(s"Max retries exceeded: ${error.getMessage}")
      )
      stepExecutions(stepExecutionId) = failedStepExecution
      
      val execution = workflowExecutions(stepExecution.workflowExecutionId)
      val failedExecution = execution.copy(
        status = Failed,
        error = Some(s"Step ${step.id} failed: ${error.getMessage}"),
        completedAt = Some(LocalDateTime.now())
      )
      workflowExecutions(stepExecution.workflowExecutionId) = failedExecution
    }
  }

  /**
   * 各種ステップタイプの実行
   */
  private def executeDataProcessingStep(step: WorkflowStep, input: Map[String, JsValue]): Map[String, JsValue] = {
    // データ処理ステップの実装
    Map("processed" -> Json.toJson(true), "records" -> Json.toJson(100))
  }

  private def executeNotificationStep(step: WorkflowStep, input: Map[String, JsValue]): Map[String, JsValue] = {
    // 通知ステップの実装
    Map("notification_sent" -> Json.toJson(true), "recipients" -> Json.toJson(5))
  }

  private def executeValidationStep(step: WorkflowStep, input: Map[String, JsValue]): Map[String, JsValue] = {
    // バリデーションステップの実装
    Map("validation_passed" -> Json.toJson(true), "errors" -> Json.toJson(0))
  }

  private def executeTransformationStep(step: WorkflowStep, input: Map[String, JsValue]): Map[String, JsValue] = {
    // データ変換ステップの実装
    Map("transformed" -> Json.toJson(true), "output_format" -> Json.toJson("json"))
  }

  private def executeExternalApiStep(step: WorkflowStep, input: Map[String, JsValue]): Map[String, JsValue] = {
    // 外部API呼び出しステップの実装
    Map("api_called" -> Json.toJson(true), "response_code" -> Json.toJson(200))
  }

  /**
   * ワークフロー実行の取得
   */
  def getWorkflowExecution(executionId: String): Future[Option[WorkflowExecution]] = {
    Future.successful(workflowExecutions.get(executionId))
  }

  /**
   * ワークフロー実行の一覧取得
   */
  def getWorkflowExecutions(
    workflowId: Option[String] = None,
    status: Option[WorkflowStatus] = None,
    limit: Int = 50
  ): Future[List[WorkflowExecution]] = {
    val filteredExecutions = workflowExecutions.values.filter { execution =>
      (workflowId.isEmpty || execution.workflowId == workflowId.get) &&
      (status.isEmpty || execution.status == status.get)
    }.toList.sortBy(_.startedAt)(Ordering[LocalDateTime].reverse).take(limit)
    
    Future.successful(filteredExecutions)
  }

  /**
   * ワークフロー実行の停止
   */
  def stopWorkflowExecution(executionId: String): Future[Boolean] = {
    workflowExecutions.get(executionId) match {
      case Some(execution) if execution.status == Running || execution.status == Pending =>
        val stoppedExecution = execution.copy(
          status = Cancelled,
          completedAt = Some(LocalDateTime.now())
        )
        workflowExecutions(executionId) = stoppedExecution
        Future.successful(true)
      case _ =>
        Future.successful(false)
    }
  }

  /**
   * ワークフロー実行の一時停止
   */
  def pauseWorkflowExecution(executionId: String): Future[Boolean] = {
    workflowExecutions.get(executionId) match {
      case Some(execution) if execution.status == Running =>
        val pausedExecution = execution.copy(status = Paused)
        workflowExecutions(executionId) = pausedExecution
        Future.successful(true)
      case _ =>
        Future.successful(false)
    }
  }

  /**
   * ワークフロー実行の再開
   */
  def resumeWorkflowExecution(executionId: String): Future[Boolean] = {
    workflowExecutions.get(executionId) match {
      case Some(execution) if execution.status == Paused =>
        val resumedExecution = execution.copy(status = Running)
        workflowExecutions(executionId) = resumedExecution
        
        // ワークフローの実行を再開
        workflowDefinitions.get(execution.workflowId).foreach(executeNextStep(executionId, _))
        
        Future.successful(true)
      case _ =>
        Future.successful(false)
    }
  }

  /**
   * ワークフロー統計の取得
   */
  def getWorkflowStatistics(): Future[Map[String, Any]] = {
    val executions = workflowExecutions.values.toList
    val totalExecutions = executions.length
    val completedExecutions = executions.count(_.status == Completed)
    val failedExecutions = executions.count(_.status == Failed)
    val runningExecutions = executions.count(_.status == Running)
    
    val averageExecutionTime = if (completedExecutions > 0) {
      val completedTimes = executions
        .filter(_.status == Completed)
        .filter(_.completedAt.isDefined)
        .map(e => Duration.between(e.startedAt, e.completedAt.get).toMinutes)
      if (completedTimes.nonEmpty) completedTimes.sum.toDouble / completedTimes.length else 0.0
    } else 0.0
    
    Future.successful(Map(
      "totalExecutions" -> totalExecutions,
      "completedExecutions" -> completedExecutions,
      "failedExecutions" -> failedExecutions,
      "runningExecutions" -> runningExecutions,
      "successRate" -> (if (totalExecutions > 0) completedExecutions.toDouble / totalExecutions else 0.0),
      "averageExecutionTimeMinutes" -> averageExecutionTime,
      "workflowDefinitions" -> workflowDefinitions.size,
      "lastUpdated" -> LocalDateTime.now().toString
    ))
  }

  /**
   * ワークフロー実行のクリーンアップ
   */
  def cleanupOldExecutions(daysToKeep: Int = 30): Future[Int] = {
    val cutoffDate = LocalDateTime.now().minusDays(daysToKeep)
    val oldExecutions = workflowExecutions.values
      .filter(_.startedAt.isBefore(cutoffDate))
      .filter(_.status == Completed || _.status == Failed || _.status == Cancelled)
      .map(_.id)
      .toList
    
    oldExecutions.foreach { executionId =>
      workflowExecutions.remove(executionId)
      stepExecutions.filter(_._2.workflowExecutionId == executionId).keys.foreach { stepId =>
        stepExecutions.remove(stepId)
      }
    }
    
    Future.successful(oldExecutions.length)
  }

  /**
   * ヘルパーメソッド
   */
  private def generateExecutionId(): String = {
    val timestamp = System.currentTimeMillis()
    val random = scala.util.Random.alphanumeric.take(8).mkString
    s"wf_${timestamp}_$random"
  }

  private def generateStepExecutionId(): String = {
    val timestamp = System.currentTimeMillis()
    val random = scala.util.Random.alphanumeric.take(8).mkString
    s"step_${timestamp}_$random"
  }
}
