package controllers

import models.{Tea, TeaStatusType}
import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneAppPerTest
import play.api.test.Injecting
import play.api.test.Helpers._
import play.api.test.FakeRequest
import scala.concurrent.Await
import scala.concurrent.duration._

class TeaControllerSpec extends PlaySpec with GuiceOneAppPerTest with Injecting {
  "TeaController" should {
    "茶葉一覧を取得できる" in {
      val controller = inject[TeaController]
      val request = FakeRequest(GET, "/api/teas")
        .withHeaders("Authorization" -> "Bearer test-token")

      val result = controller.index()(request)
      status(result) mustBe OK
    }

    "茶葉を作成できる" in {
      val controller = inject[TeaController]
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

      val request = FakeRequest(POST, "/api/teas")
        .withHeaders("Authorization" -> "Bearer test-token")
        .withJsonBody(play.api.libs.json.Json.toJson(tea))

      val result = controller.create()(request)
      status(result) mustBe CREATED
    }

    "茶葉を更新できる" in {
      val controller = inject[TeaController]
      val tea = Tea(
        id = Some(1L),
        userId = 1L,
        name = "更新された茶葉",
        teaTypeId = 1L,
        purchaseDate = java.time.LocalDate.now(),
        status = TeaStatusType.New,
        notes = Some("テストメモ"),
        createdAt = java.time.LocalDateTime.now(),
        updatedAt = java.time.LocalDateTime.now()
      )

      val request = FakeRequest(PUT, s"/api/teas/${tea.id.get}")
        .withHeaders("Authorization" -> "Bearer test-token")
        .withJsonBody(play.api.libs.json.Json.toJson(tea))

      val result = controller.update(tea.id.get)(request)
      status(result) mustBe OK
    }

    "茶葉を削除できる" in {
      val controller = inject[TeaController]
      val request = FakeRequest(DELETE, "/api/teas/1")
        .withHeaders("Authorization" -> "Bearer test-token")

      val result = controller.delete(1L)(request)
      status(result) mustBe NO_CONTENT
    }
  }
} 