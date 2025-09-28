name := "tea-growth-record"
version := "1.0-SNAPSHOT"
scalaVersion := "2.13.12"

lazy val root = (project in file("."))
  .enablePlugins(PlayScala)
  .settings(
    libraryDependencies ++= Seq(
      guice,
      "com.typesafe.play" %% "play-slick" % "5.2.0",
      "com.typesafe.play" %% "play-slick-evolutions" % "5.2.0",
      "mysql" % "mysql-connector-java" % "8.0.33",
      "com.typesafe.play" %% "play-json" % "2.8.2",
      "com.typesafe.play" %% "play-json-joda" % "2.8.2",
      "org.scalatestplus.play" %% "scalatestplus-play" % "6.0.0" % Test,
      "org.scala-lang.modules" %% "scala-xml" % "2.2.0"
    ),
    javaOptions ++= Seq(
      "-Dconfig.file=conf/application.conf"
    ),
    scalacOptions ++= Seq(
      "-deprecation",
      "-feature",
      "-unchecked",
      "-Xlint"
    ),
    javacOptions ++= Seq("-source", "11", "-target", "11"),
    evictionErrorLevel := Level.Warn,
    libraryDependencySchemes += "org.scala-lang.modules" %% "scala-xml" % VersionScheme.Always
  )

resolvers += "Typesafe repository" at "https://repo.typesafe.com/typesafe/releases/"
resolvers += "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"
resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"

val playVersion = "2.8.19"

libraryDependencies ++= Seq(
  guice,
  "com.typesafe.play" %% "play-slick" % "5.0.0",
  "com.typesafe.play" %% "play-slick-evolutions" % "5.0.0",
  "mysql" % "mysql-connector-java" % "8.0.28",
  "com.typesafe.play" %% "play-mailer" % "8.0.1",
  "com.typesafe.play" %% "play-mailer-guice" % "8.0.1",
  "org.joda" % "joda-convert" % "2.0.2",
  "com.github.tototoshi" %% "slick-joda-mapper" % "2.5.0",
  "org.mindrot" % "jbcrypt" % "0.4",
  "com.typesafe.play" %% "play-json" % "2.8.2",
  "com.typesafe.play" %% "play-json-joda" % "2.8.2",
  "org.scalatestplus.play" %% "scalatestplus-play" % "5.1.0" % Test,
  "com.typesafe.play" %% "play-jdbc" % playVersion,
  "com.typesafe.play" %% "play-jdbc-evolutions" % playVersion,
  "com.typesafe.play" %% "play-cache" % playVersion,
  "com.typesafe.play" %% "play-ws" % playVersion,
  "com.typesafe.play" %% "play-ahc-ws-standalone" % "2.1.10"
)

// 依存関係の競合を解決
ThisBuild / libraryDependencySchemes ++= Seq(
  "org.scala-lang.modules" %% "scala-java8-compat" % VersionScheme.Always
)

// Play Framework の設定
PlayKeys.playDefaultPort := 9000

// アセットの設定
Assets / pipelineStages := Seq(digest, gzip)
pipelineStages := Seq(digest, gzip)

// テスト設定
testOptions in Test += Tests.Argument(TestFrameworks.ScalaTest, "-oD")

// 開発時の設定
fork in run := true
connectInput in run := true
outputStrategy := Some(StdoutOutput) 