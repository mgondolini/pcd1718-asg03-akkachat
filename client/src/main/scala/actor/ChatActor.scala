package actor

import akka.actor.Actor
import controller.Controller

class ChatActor(controller: Controller) extends Actor{


  override def receive: Receive = roomManagement

  private def roomManagement: Receive = ???


}
