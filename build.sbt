name := "tea-growth-record"
organization := "com.example"

version := "1.0-SNAPSHOT"

scalaVersion := "2.13.12"

val playVersion = "2.8.0"

lazy val root = (project in file("."))
  .enablePlugins(PlayScala)
  .disablePlugins(
    play.sbt.PlayWeb,
    com.typesafe.sbt.web.SbtWeb,
    com.typesafe.sbt.jse.SbtJsEngine,
    com.typesafe.sbt.jse.SbtJsTask,
    com.typesafe.sbt.jse.SbtJs,
    com.typesafe.sbt.web.SbtWeb,
    com.typesafe.sbt.digest.SbtDigest,
    com.typesafe.sbt.rjs.SbtRjs,
    com.typesafe.sbt.less.SbtLess,
    com.typesafe.sbt.coffeescript.SbtCoffeeScript,
    com.typesafe.sbt.jshint.SbtJshint,
    com.typesafe.sbt.mocha.SbtMocha
  )
  .settings(
    libraryDependencies ++= Seq(
      guice,
      "com.typesafe.play" %% "play-slick" % "5.2.0",
      "com.typesafe.play" %% "play-slick-evolutions" % "5.2.0",
      "mysql" % "mysql-connector-java" % "8.0.33",
      "com.typesafe.play" %% "play-mailer" % "8.0.1",
      "com.typesafe.play" %% "play-mailer-guice" % "8.0.1",
      "org.joda" % "joda-convert" % "2.0.2",
      "com.github.tototoshi" %% "slick-joda-mapper" % "2.5.0",
      "org.mindrot" % "jbcrypt" % "0.4",
      "com.typesafe.play" %% "play-json" % "2.8.0",
      "com.typesafe.play" %% "play-json-joda" % "2.8.0",
      "com.typesafe.play" %% "play-jdbc" % playVersion,
      "com.typesafe.play" %% "play-jdbc-evolutions" % playVersion,
      "com.typesafe.play" %% "play-cache" % playVersion,
      "com.typesafe.play" %% "play-ws" % playVersion,
      "com.typesafe.play" %% "play-ahc-ws-standalone" % "2.1.10",
      "org.scalatestplus.play" %% "scalatestplus-play" % "5.1.0" % Test,
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
    libraryDependencySchemes += "org.scala-lang.modules" %% "scala-xml" % VersionScheme.Always,
    libraryDependencySchemes += "org.scala-lang.modules" %% "scala-java8-compat" % VersionScheme.Always,
    PlayKeys.playDefaultPort := 9000,
    testOptions += Tests.Argument(TestFrameworks.ScalaTest, "-oD"),
    fork := true,
    connectInput := true,
    outputStrategy := Some(StdoutOutput)
  )

// Resolvers for dependency resolution
resolvers ++= Seq(
  "Maven Central" at "https://repo1.maven.org/maven2/",
  "Typesafe Releases" at "https://repo.typesafe.com/typesafe/releases/",
  "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"
) 