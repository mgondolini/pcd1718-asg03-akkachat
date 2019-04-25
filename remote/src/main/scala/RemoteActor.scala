
import akka.actor.{Actor, ActorRef, ActorSystem, Props, Stash}
import com.typesafe.config.{Config, ConfigFactory}
import config.ActorConfig.RemoteActorInfo
import config.Config.{enterCS, exitCS}
import messages.ChatRequest.{AddParticipant, CSrequest, MessageRequest, RemoveParticipant}
import messages.ChatResponse.{CSaccepted, DispatchMessage, ExitSuccess}

import scala.collection.mutable.ListBuffer

/**
  * @author Monica Gondolini
  */
class RemoteActor extends Actor with Stash{

  private var participants: ListBuffer[ActorRef] = new ListBuffer[ActorRef]
  private var CSuser: Option[String] = None
  private var cs: Boolean = false
  private var count: Int = 0

  override def receive: Receive = {
    case AddParticipant() => addToParticipantsList(sender)
    case MessageRequest(message, username, timestamp) => examineMessage(message, username, timestamp)
    case CSaccepted(username) =>
      for(_ <- participants)
        count += 1
      if(count == participants.size)
        setCS(username)
    case RemoveParticipant(username) =>
      removeFromParticipantsList(sender)
      if (cs && CSuser.get.equals(username))
        unsetCS()
      sender ! ExitSuccess()
  }

  private def addToParticipantsList(actorIdentity: ActorRef){
    participants += actorIdentity
    println("User in list (add option) -> " + actorInList)
  }

  private def removeFromParticipantsList(actorIdentity: ActorRef) {
    participants -= actorIdentity
    println("User in list (remove option) -> " + actorInList)
  }

  private def examineMessage(message: String, username: String, timestamp: String): Unit = {
    message match {
      case `enterCS` =>
        println(enterCS+": "+username)
        participants foreach{actor => actor ! CSrequest(username)}
      case `exitCS` =>
        println(exitCS+": "+username)
        unsetCS()
      case _ =>
        if (cs && CSuser.get.equals(username)) participants foreach{actor => actor ! DispatchMessage(message, username, timestamp)}
        else if (!cs && CSuser.isEmpty) participants foreach{actor => actor ! DispatchMessage(message, username, timestamp)}
        else stash()
    }
  }

  private def actorInList(): ListBuffer[ActorRef]  = participants

  private def setCSuser(username: Option[String]): Unit = CSuser = username

  private def setCS(username:String): Unit = {
    cs = true
    setCSuser(Some(username))
  }

  private def unsetCS(): Unit = {
    count = 0
    cs = false
    setCSuser(None)
  }
}

object RemoteActor {
  def main(args: Array[String])  {
    val config: Config = ConfigFactory.load(RemoteActorInfo.Configuration)
    val system = ActorSystem(RemoteActorInfo.Context, config)
    system.actorOf(Props[RemoteActor], name = RemoteActorInfo.Name)
  }
}