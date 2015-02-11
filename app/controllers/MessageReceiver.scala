package controllers

import akka.actor.Actor
import akka.actor.Props

class MessageReceiver extends Actor {
  def receive: Receive = {
    case "readLine" => {
      println("readLine: " + MessageReceiver.b)
      sender ! MessageReceiver.b.toString
    }
    case str => {
      MessageReceiver.b.append(str)
      println(MessageReceiver.b)
      sender ! MessageReceiver.b.toString
    }
  }
}

object MessageReceiver {
  def apply() = Props(classOf[MessageReceiver])
  val b = new StringBuilder
}
