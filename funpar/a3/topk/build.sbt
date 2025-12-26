val scala3Version = "3.6.3"

lazy val root = project
  .in(file("."))
  .settings(
    name := "topk",
    version := "0.0.1",

    scalaVersion := scala3Version,
  )
