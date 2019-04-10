package actor

import akka.actor.{Actor, ActorSelection}
import config.ActorConfig.ActorPath.AuthenticationActorPath
import config.ActorConfig.ActorSystemInfo.system
import config.ActorConfig.RemoteActorInfo
import controller.ChatRoomController
import messages.ChatAuthentication.UserRequest
import messages.ChatBehaviour._

class ChatRoomActor() extends Actor {

  val authenticationActor: ActorSelection = system actorSelection AuthenticationActorPath
  val remoteActor: ActorSelection = system actorSelection RemoteActorInfo.Path
  var chatRoomController: ChatRoomController = _

  override def receive: Receive = chatBehaviour

  private def chatBehaviour: Receive = {
    case SetController(controller) =>
      chatRoomController = controller
      authenticationActor ! UserRequest()
    case SetUser(user) =>
      chatRoomController.setUser(user)
      remoteActor ! AddParticipant(user.username)
    case SendMessage(message, username) => remoteActor ! MessageRequest(message, username)
    case QuitChat() => chatRoomController.exitChatView()
    case DispatchMessage(message, username) => chatRoomController.messagesArea.setText(username+": "+message)
  }

}
