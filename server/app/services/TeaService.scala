package services

import javax.inject.{Inject, Singleton}
import models.{Tea, TeaStatusType}
import repositories.TeaRepository
import scala.concurrent.{ExecutionContext, Future}
import scala.math.BigDecimal

/**
 * お茶の管理に関するサービスクラス
 */
@Singleton
class TeaService @Inject()(
  teaRepository: TeaRepository
)(implicit ec: ExecutionContext) {

  def findByUserId(userId: Long): Future[Seq[Tea]] = {
    teaRepository.findByUserId(userId)
  }

  def findById(id: Long): Future[Option[Tea]] = {
    teaRepository.findById(id)
  }

  def create(tea: Tea): Future[Option[Tea]] = {
    teaRepository.create(tea)
  }

  def update(tea: Tea): Future[Option[Tea]] = {
    teaRepository.update(tea)
  }

  def delete(id: Long): Future[Boolean] = {
    teaRepository.delete(id)
  }

  def updateStatus(id: Long, status: TeaStatusType.TeaStatusType): Future[Boolean] = {
    teaRepository.updateStatus(id, status)
  }

  def updateQuantity(id: Long, quantity: BigDecimal): Future[Boolean] = {
    teaRepository.updateQuantity(id, quantity)
  }
}