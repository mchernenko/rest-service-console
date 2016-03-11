package controllers

import actors.WebSocketActor
import akka.actor.ActorRef
import play.api.mvc.WebSocket
import play.api.mvc.Controller
import play.api.Play.current

import scala.collection.mutable

object WebSocketController extends Controller {

  val connections = mutable.Set.empty[ActorRef]

  def sendMessageToAll(msg: String) = connections.foreach(_ ! msg)

  def restfulConsole = WebSocket.acceptWithActor[String, String] {
    request => out => {
      connections.add(out)
      WebSocketActor.props(out)
    }
  }
}
