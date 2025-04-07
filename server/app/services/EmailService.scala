package services

import javax.inject.{Inject, Singleton}
import play.api.Configuration
import play.api.libs.mailer._
import scala.concurrent.{ExecutionContext, Future}

/**
 * メール送信サービス
 */
@Singleton
class EmailService @Inject()(
  mailerClient: MailerClient,
  config: Configuration
)(implicit ec: ExecutionContext) {

  private val from = config.get[String]("email.from")
  private val baseUrl = config.get[String]("application.baseUrl")

  /**
   * パスワードリセットメールを送信
   */
  def sendPasswordResetEmail(email: String, token: String): Future[Unit] = Future {
    val subject = "パスワードのリセット"
    val resetUrl = s"$baseUrl/reset-password?token=$token"
    val body = views.html.emails.resetPassword(resetUrl).toString
    val email = Email(
      subject = subject,
      from = from,
      to = Seq(email),
      bodyHtml = Some(body)
    )
    mailerClient.send(email)
  }

  /**
   * 新規登録確認メールを送信
   */
  def sendWelcomeEmail(email: String, name: Option[String]): Future[Unit] = Future {
    val subject = "ようこそ Tea Growth Record へ"
    val body = views.html.emails.welcome(name.getOrElse("ユーザー")).toString
    val email = Email(
      subject = subject,
      from = from,
      to = Seq(email),
      bodyHtml = Some(body)
    )
    mailerClient.send(email)
  }
}