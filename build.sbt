name := "NBANotifications"

version := "0.1"

scalaVersion := "2.13.1"

libraryDependencies ++= Seq(

  "com.softwaremill.sttp.client" %% "core" % "2.0.0-RC1",
  "io.circe" %% "circe-core" % "0.12.3",
  "io.circe" %% "circe-generic" % "0.12.3",
  "io.circe" %% "circe-parser" % "0.12.3"
)
