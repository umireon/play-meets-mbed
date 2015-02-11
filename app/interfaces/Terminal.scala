package interfaces

import com.github.jodersky.flow.Serial
import com.github.jodersky.flow.Serial.Opened
import com.github.jodersky.flow.Serial.Received
import com.github.jodersky.flow.SerialSettings

import akka.actor.Actor
import akka.actor.ActorRef
import akka.actor.Props
import akka.io.IO

class Terminal(port: String, settings: SerialSettings, reader: ActorRef) extends Actor {
  import context.system

  IO(Serial) ! Serial.Open(port, settings)

  def receive = {
    case Opened(port) => {
      val operator = sender
      context become opened(operator)
      context watch operator
    }
  }

  def opened(operator: ActorRef): Receive = {
    case Received(data) => {
      val str = (new String(data.toArray, "UTF-8"))
      print(str)
      reader ! str
    }
  }
}

object Terminal {
  def apply(port: String, settings: SerialSettings, reader: ActorRef) = Props(classOf[Terminal], port, settings, reader)
}
