package actor

import akka.actor.Actor
import controller.ChatRoomController

class ChatRoomActor(chatRoomController: ChatRoomController) extends Actor{


  override def receive: Receive = chatBehaviour

  private def chatBehaviour: Receive = ???

}
