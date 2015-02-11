name := """play-meets-mbed"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.1"

libraryDependencies ++= Seq(
  jdbc,
  anorm,
  cache,
  ws
)

libraryDependencies += "com.github.jodersky" %% "flow" % "2.1.0"

libraryDependencies += "com.github.jodersky" % "flow-native" % "2.1.0"

