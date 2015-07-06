package services

import concurrent.duration._
import concurrent.ExecutionContext.Implicits.global
import util.{Properties, Try}
import java.nio.file.{Files, Paths}
import akka.actor.{Actor, ActorRef, PoisonPill}
import akka.event.Logging

class FileService(file: String, client: ActorRef) extends Actor {

  private val log = Logging(context.system, this)
  
  val storePath = Properties.propOrElse("fileservice.store", "")
  val frequency = Properties.propOrElse("fileservice.frequency", "0")
  
  private var lines: Iterator[String] = _
  
  override def preStart() {
    if (!Files.exists(Paths.get(s"$storePath/$file"))) {
      client ! s"File $storePath/$file does not exist"
      self ! PoisonPill
    }
    else {
      val freq = Try(frequency.toInt).getOrElse(0)
      lines = io.Source.fromFile(s"$storePath/$file").getLines
      context.system.scheduler.schedule(0 millis, freq millis, self, "chunk")
    }
  }
  
  def receive(): Receive = {
    case "chunk" if lines.hasNext => client ! lines.next()
    case "chunk" => self ! PoisonPill
  }
  
}
