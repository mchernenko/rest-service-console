package actors

import akka.actor.{Props, ActorRef, Actor}

import scala.collection.mutable

class WebSocketActor(out: ActorRef) extends Actor{
  def receive = {
    case message => out ! "Connection accepted"
  }
}

object WebSocketActor {
  def props(out: ActorRef) = {
    Props(new WebSocketActor(out))
  }
}
