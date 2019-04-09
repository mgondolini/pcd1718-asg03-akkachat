package actor

import akka.actor.{Actor, ActorSelection}
import config.ActorConfig.ActorPath.ChatActorPath
import config.ActorConfig.ActorSystemInfo.system
import controller.ChatRoomController
import messages.ChatBehaviour.{QuitChat, SendMessage, SetController}

class ChatRoomActor(_chatRoomController: ChatRoomController) extends Actor {

  val chatActor: ActorSelection = system actorSelection ChatActorPath
  var chatRoomController: ChatRoomController = _chatRoomController

  override def receive: Receive = chatBehaviour

  private def chatBehaviour: Receive = {
    case SendMessage(message) => ???
    case QuitChat() => ???
    case SetController(controller) => chatRoomController = controller
  }

}
