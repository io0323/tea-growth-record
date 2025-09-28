package models

import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneAppPerTest
import play.api.test.Injecting

class TeaSpec extends PlaySpec with GuiceOneAppPerTest with Injecting {
  "Tea" should {
    "正しく初期化できる" in {
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

      tea.name mustBe "テスト茶葉"
      tea.teaTypeId mustBe 1L
      tea.status mustBe TeaStatusType.New
      tea.notes mustBe Some("テストメモ")
    }
  }
} 