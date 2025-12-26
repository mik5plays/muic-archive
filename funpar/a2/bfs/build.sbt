val scala3Version = "3.7.3"

lazy val root = project
  .in(file("."))
  .settings(
    name := "BFS",
    version := "0.0.1",

    scalaVersion := scala3Version,
  )
