package services

import models.{Tea, TeaStatusType}
import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneAppPerTest
import play.api.test.Injecting
import scala.concurrent.Await
import scala.concurrent.duration._

class TeaServiceSpec extends PlaySpec with GuiceOneAppPerTest with Injecting {
  "TeaService" should {
    "茶葉を作成できる" in {
      val service = inject[TeaService]
      val tea = Tea(
        id = None,
        userId = 1L,
        name = "テスト茶葉",
        teaTypeId = 1L,
        purchaseDate = java.time.LocalDate.now(),
        status = TeaStatusType.New,
        notes = Some("テストメモ"),
        createdAt = java.time.LocalDateTime.now(),
        updatedAt = java.time.LocalDateTime.now()
      )

      val result = Await.result(service.create(tea), 5.seconds)
      result.id mustBe defined
      result.name mustBe "テスト茶葉"
    }

    "茶葉を更新できる" in {
      val service = inject[TeaService]
      val tea = Tea(
        id = None,
        userId = 1L,
        name = "テスト茶葉",
        teaTypeId = 1L,
        purchaseDate = java.time.LocalDate.now(),
        status = TeaStatusType.New,
        notes = Some("テストメモ"),
        createdAt = java.time.LocalDateTime.now(),
        updatedAt = java.time.LocalDateTime.now()
      )

      val created = Await.result(service.create(tea), 5.seconds)
      val updated = created.copy(name = "更新された茶葉")
      val result = Await.result(service.update(updated), 5.seconds)

      result mustBe true
    }

    "茶葉を削除できる" in {
      val service = inject[TeaService]
      val tea = Tea(
        id = None,
        userId = 1L,
        name = "テスト茶葉",
        teaTypeId = 1L,
        purchaseDate = java.time.LocalDate.now(),
        status = TeaStatusType.New,
        notes = Some("テストメモ"),
        createdAt = java.time.LocalDateTime.now(),
        updatedAt = java.time.LocalDateTime.now()
      )

      val created = Await.result(service.create(tea), 5.seconds)
      val result = Await.result(service.delete(created.id.get, 1L), 5.seconds)

      result mustBe true
    }
  }
} 