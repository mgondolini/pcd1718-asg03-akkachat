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
  var remoteActor: ActorSelection = _
  var chatRoomController: ChatRoomController = _


  override def preStart(): Unit = {
    remoteActor = context actorSelection RemoteActorInfo.Path
    println(remoteActor)
  }

  override def receive: Receive = chatRequest orElse chatResponse

  private def chatRequest: Receive = {
    case SetController(controller) =>
      chatRoomController = controller
      authenticationActor ! UserRequest()
    case SetUser(user) =>
      chatRoomController.setUser(user)
      remoteActor ! AddParticipant(user.username)
    case SendMessage(message, username) => remoteActor ! MessageRequest(message, username)
    case QuitChat() => remoteActor ! RemoveParticipant(chatRoomController.user.username)
  }

  private def chatResponse: Receive = {
    case ExitSuccess() => chatRoomController.exitChatView()
    case DispatchMessage(message, username) => chatRoomController.displayMessage(username+": "+message)
  }

}
