package controllers

import akka.actor.Props
import play.api.mvc._
import play.api.Play.current

import services.FileService

object Application extends Controller {
  
  def serveFile(path: String) = WebSocket.acceptWithActor[String, String] { _ => out => Props(classOf[FileService], path, out) }
  
}

