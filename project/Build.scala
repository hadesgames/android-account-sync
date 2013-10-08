import sbt._
import Keys._

import sbtandroid.AndroidPlugin._
import org.sbtidea.SbtIdeaPlugin._
object AndroidBuild extends Build {
  val dependencies = Seq(
    "org.scaloid" %% "scaloid" % "2.3-8",
    "uk.co.bigbeeconsultants" %% "bee-client" % "0.21.+",
    "se.fishtank" %% "css-selectors-scala" % "0.1.3",
    "org.ccil.cowan.tagsoup" % "tagsoup" % "1.2" % "provided",
    "org.macroid" %% "macroid" % "1.0.0-RC1",
    "io.dylemma" %% "scala-frp" % "1.0"
  )

  val preloads = Seq(
    filterModule("org.scaloid" % "scaloid_2.10" % "2.3-8" ),
    filterModule("org.scalaz" % "scalaz-core_2.10" % "7.0.3"),
    filterModule("org.scala-lang" % "scala-reflect" % "2.10.3-RC2")
  )

  val customResolvers = Seq(
    "Maven Central Server" at "http://repo1.maven.org/maven2",
    "Big Bee Consultants" at "http://repo.bigbeeconsultants.co.uk/repo",
    "JCenter" at "http://jcenter.bintray.com"
  )

  val globalSettings = Seq(
    name := "FbSync",
    version := "0.1",
    versionCode := 0,
    scalaVersion := "2.10.2",
    platformName := "android-18",
    keyalias := "change-me",
    libraryDependencies ++= dependencies,
    preloadFilters ++= preloads,
    resolvers ++= customResolvers,
    dxMemory := "-JXmx3096m"
  )

  lazy val main = AndroidProject(
    "main",
    file("."),
    settings=globalSettings
  )

  val worksheetSettings = Seq(
    name := "worksheets",
    scalaVersion := "2.10.2",
    version := "1.0",
    versionCode :=0,
    keyalias := "change-me2"
  )

  lazy val worksheets = Project(id="worksheets", base = file("./worksheets"), settings = Project.defaultSettings ++ Seq(scalaVersion := "2.10.2", libraryDependencies ++= dependencies, resolvers ++= customResolvers))

/*
  lazy val tests = AndroidTestProject(
    "tests",
    file("tests"),
    settings=globalSettings)
    .dependsOn(main % "provided")
    .settings(name := "MockaTests") */
}

// vim: set ts=2 sw=2 et:
