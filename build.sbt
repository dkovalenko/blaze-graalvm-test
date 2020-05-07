import Dependencies._

ThisBuild / scalaVersion     := "2.13.2"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "com.example"
ThisBuild / organizationName := "example"

lazy val root = (project in file("."))
  .settings(
    name := "blaze-graal-ni-test",
    libraryDependencies += scalaTest % Test,
    libraryDependencies ++= Seq(
      "org.http4s"                %% "http4s-blaze-core"      % "0.21.4",
    )
  )

scalacOptions ++= Seq(
  "-target:jvm-1.8",
  "-encoding",
  "UTF-8",
  "-unchecked",
  "-deprecation",
  "-Ywarn-dead-code",
  "-Ywarn-numeric-widen",
  "-Ywarn-value-discard",
  "-Ywarn-unused",
  "-Ymacro-annotations"
  //  "-Xfatal-warnings"
)



Global / autoStartServer := false
Global / onChangedBuildSource := ReloadOnSourceChanges