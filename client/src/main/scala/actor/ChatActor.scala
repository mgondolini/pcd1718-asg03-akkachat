package actor

import java.text.SimpleDateFormat
import java.util.{Calendar, Date}

import akka.actor.{Actor, ActorRef, ActorSelection}
import config.ActorConfig.ActorPath.AuthenticationActorPath
import config.ActorConfig.ActorSystemInfo.system
import config.ActorConfig.RemoteActorInfo
import controller.ChatController
import messages.ChatAuthentication.UserRequest
import messages.ChatRequest._
import messages.ChatResponse.{CSaccepted, DispatchMessage, ExitSuccess}
import model.User

import scala.collection.mutable.ListBuffer

/**
  * @author Monica Gondolini
  */
class ChatActor() extends Actor {

  val authenticationActor: ActorSelection = system actorSelection AuthenticationActorPath
  var remoteActor: ActorSelection = _
  var chatController: ChatController = _
  var timestamp: String = _
  var CSqueue: ListBuffer[String] = new ListBuffer[String]
  val sdf: SimpleDateFormat =  new SimpleDateFormat("HH.mm.ss")

  override def preStart(): Unit = remoteActor = context actorSelection RemoteActorInfo.Path

  override def receive: Receive = chatRequest orElse chatResponse

  private def chatRequest: Receive = {
    case SetController(controller) =>
      chatController = controller
      authenticationActor ! UserRequest()
    case SetUser(user) =>
      chatController.setUser(user)
      remoteActor ! AddParticipant()
    case SendMessage(message, username) =>
      timestamp = sdf.format(new Date)
      remoteActor ! MessageRequest(message, username, timestamp)
      Thread.sleep(500)
    case QuitChat() => remoteActor ! RemoveParticipant(chatController.user.username)
    case CSrequest(username, ts) =>

      val tsDate: Date = sdf.parse(ts)
      val timestampDate: Date = sdf.parse(timestamp)

      if (CSqueue.nonEmpty && !username.equals(CSqueue.head)) {
        remoteActor ! CSaccepted(CSqueue.head)
      } else {
        if (timestampDate after tsDate) remoteActor ! CSaccepted(username)
        else if (timestampDate before tsDate) CSqueue += username
        else remoteActor ! CSaccepted(username)
      }
  }

  private def chatResponse: Receive = {
    case ExitSuccess() => chatController.exitChatView()
    case DispatchMessage(message, username, _timestamp) => chatController.displayMessage(username+": "+message+ "\t\t" + _timestamp)
  }

}
