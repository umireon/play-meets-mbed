package controllers

import play.api._
import play.api.mvc._

import akka.actor._
import akka.pattern.ask
import akka.pattern.pipe

import com.github.jodersky.flow.Parity
import com.github.jodersky.flow.SerialSettings
import com.github.jodersky.flow.Serial.Write

import akka.actor.ActorSystem

import interfaces.Terminal

import scala.concurrent.Await

import scala.concurrent.duration._
import akka.util.Timeout

object Application extends Controller {
  def index = Action {
    val port = "/dev/cu.usbmodem1423"
    val settings = SerialSettings(
      baud = 9600,
      characterSize = 8,
      twoStopBits = false,
      parity = Parity.None
    )

    implicit val timeout = Timeout(10 seconds)

    val system = ActorSystem("flow")
    val rcv = system.actorOf(MessageReceiver())
    val terminal = system.actorOf(Terminal(port, settings, rcv))
    Thread.sleep(2)
    val future = rcv ? "readLine"
    val result = Await.result(future, timeout.duration)

    //Ok(views.html.index("mbed: " + result))
    Ok("mbed: " + result)
  }
}

