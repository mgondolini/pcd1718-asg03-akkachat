package actor

import akka.actor.{Actor, ActorSelection}
import config.ActorConfig.ActorPath.AuthenticationActorPath
import config.ActorConfig.ActorSystemInfo.system
import config.ActorConfig.RemoteSystemInfo.remoteSystem
import config.ActorConfig.RemoteActorInfo
import controller.ChatRoomController
import messages.ChatAuthentication.UserRequest
import messages.ChatBehaviour._

class ChatRoomActor() extends Actor {

  val authenticationActor: ActorSelection = system actorSelection AuthenticationActorPath
  var remoteActor: ActorSelection = _
  var chatRoomController: ChatRoomController = _


  @throws[Exception](classOf[Exception])
  override def preStart(): Unit = {
    remoteActor = context actorSelection RemoteActorInfo.Path
    println(remoteActor)
  }

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
