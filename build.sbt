name := "tea-growth-record"
organization := "com.example"

version := "1.0-SNAPSHOT"

lazy val root = (project in file("."))
  .aggregate(server)

lazy val server = (project in file("server"))
  .enablePlugins(PlayScala)

scalaVersion := "2.13.12" 