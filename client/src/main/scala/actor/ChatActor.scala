package actor

import java.text.SimpleDateFormat
import java.util.Date

import akka.actor.{Actor, ActorSelection}
import config.ActorConfig.ActorPath.AuthenticationActorPath
import config.ActorConfig.ActorSystemInfo.system
import config.ActorConfig.RemoteActorInfo
import controller.ChatController
import messages.ChatAuthentication.UserRequest
import messages.ChatBehaviour._
import model.User

/**
  * @author Monica Gondolini
  */
class ChatActor() extends Actor {

  val authenticationActor: ActorSelection = system actorSelection AuthenticationActorPath
  var remoteActor: ActorSelection = _
  var chatController: ChatController = _
  var chatUser: User = _

  override def preStart(): Unit = remoteActor = context actorSelection RemoteActorInfo.Path
  override def receive: Receive = chatRequest orElse chatResponse

  private def chatRequest: Receive = {
    case SetController(controller) =>
      chatController = controller
      authenticationActor ! UserRequest()
    case SetUser(user) =>
      setUser(user)
      chatController.setUser(user)
      remoteActor ! AddParticipant(user.username)
    case SendMessage(message, username) =>
      val timestamp = new SimpleDateFormat("HH.mm.ss").format(new Date)
      remoteActor ! MessageRequest(message, username, timestamp)
    case QuitChat() => remoteActor ! RemoveParticipant(chatController.user.username)
    case CSrequest(username) => remoteActor ! CSaccepted(username)
  }

  private def chatResponse: Receive = {
    case ExitSuccess() => chatController.exitChatView()
    case DispatchMessage(message, username, timestamp) => chatController.displayMessage(username+": "+message+ "\t\t" + timestamp)
  }

  def setUser(_user: User): Unit = chatUser = _user

}
