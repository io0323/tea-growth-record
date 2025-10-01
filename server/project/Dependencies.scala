import sbt._

object Dependencies {
  val playVersion = "2.8.2"
  val slickVersion = "3.3.3"
  val mysqlVersion = "8.0.28"
  val playMailerVersion = "8.0.1"
  val jbcryptVersion = "0.4"

  val playDeps = Seq(
    "com.typesafe.play" %% "play" % playVersion,
    "com.typesafe.play" %% "play-guice" % playVersion,
    "com.typesafe.play" %% "play-slick" % "5.0.0",
    "com.typesafe.play" %% "play-slick-evolutions" % "5.0.0",
    "com.typesafe.play" %% "play-mailer" % playMailerVersion,
    "com.typesafe.play" %% "play-mailer-guice" % playMailerVersion,
    "com.typesafe.play" %% "play-json-joda" % "2.8.2"
  )

  val dbDeps = Seq(
    "mysql" % "mysql-connector-java" % mysqlVersion,
    "com.typesafe.slick" %% "slick" % slickVersion,
    "com.typesafe.slick" %% "slick-hikaricp" % slickVersion
  )

  val securityDeps = Seq(
    "org.mindrot" % "jbcrypt" % jbcryptVersion
  )

  val testDeps = Seq(
    "org.scalatestplus.play" %% "scalatestplus-play" % "5.1.0" % Test
  )

  val allDeps = playDeps ++ dbDeps ++ securityDeps ++ testDeps
} 