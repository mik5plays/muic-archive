val scala3Version = "3.6.3"

lazy val root = project
  .in(file("."))
  .settings(
    name := "crawler",
    version := "0.0.1",

    scalaVersion := scala3Version,
    libraryDependencies += "org.jsoup" % "jsoup" % "1.18.3"
  )
