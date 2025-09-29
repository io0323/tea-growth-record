name := "tea-growth-record"
organization := "com.example"

version := "1.0-SNAPSHOT"

val playVersion = "2.8.20"

lazy val root = (project in file("."))
  .enablePlugins(PlayScala)
  .settings(
    libraryDependencies ++= Seq(
      guice,
      "com.typesafe.play" %% "play-slick" % "5.2.0",
      "com.typesafe.play" %% "play-slick-evolutions" % "5.2.0",
      "com.typesafe.play" %% "play-filters-helpers" % playVersion,
      "mysql" % "mysql-connector-java" % "8.0.33",
      "org.joda" % "joda-convert" % "2.0.2",
      "net.logstash.logback" % "logstash-logback-encoder" % "7.4",
      "com.typesafe.play" %% "play-json-joda" % playVersion,
      "org.mindrot" % "jbcrypt" % "0.4",
      "com.typesafe.play" %% "play-ws" % playVersion
    ),
    libraryDependencies ++= Seq(
      "org.scalatestplus.play" %% "scalatestplus-play" % "6.0.0-RC2" % Test
    ),
    scalacOptions ++= Seq(
      "-feature",
      "-deprecation",
      "-Xfatal-warnings"
    )
  )

scalaVersion := "2.13.12"

// Resolvers for dependency resolution
resolvers ++= Seq(
  "Typesafe Releases" at "https://repo.typesafe.com/typesafe/releases/",
  "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots",
  "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"
)

// Server configuration
PlayKeys.devSettings += "play.server.http.port" -> "9000"

// Adds additional packages into Twirl
TwirlKeys.templateImports ++= Seq(
  "models._",
  "forms._",
  "play.api.i18n.Messages",
  "play.api.mvc.RequestHeader",
  "java.time.format.DateTimeFormatter"
)

// Adds additional packages into conf/routes
play.sbt.routes.RoutesKeys.routesImport ++= Seq(
  "models.TeaType",
  "models.TeaStatus"
) 