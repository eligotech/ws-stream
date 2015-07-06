import sbt._
import Keys._
import play.Play.autoImport._
import play.PlayScala

object WsStreamBuild extends Build {

  lazy val app: Project = Project(
    id = "ws-stream",
    base = file(".")
  )
  .enablePlugins(PlayScala).settings(
    version      := "1.0",
    scalaVersion := "2.11.4"
  )

}
