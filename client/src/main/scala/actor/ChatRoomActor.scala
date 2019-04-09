package actor

import akka.actor.{Actor, ActorSelection}
import config.ActorConfig.ActorPath.ChatActorPath
import config.ActorConfig.ActorSystemInfo.system
import controller.ChatRoomController
import messages.ChatAuthentication.UserRequest
import messages.ChatBehaviour._

class ChatRoomActor() extends Actor {

  val chatActor: ActorSelection = system actorSelection ChatActorPath
  var chatRoomController: ChatRoomController = _

  override def receive: Receive = chatBehaviour

  private def chatBehaviour: Receive = {
    case SetUser(user) => chatRoomController.setUser(user)
    case SendMessage(message, username) => ???
      //mandare cose all'attore remoto
    case QuitChat() => chatRoomController.exitChatView()
    case SetController(controller) =>
      chatRoomController = controller
      chatActor ! UserRequest()
  }

}
