
import akka.actor.{Actor, ActorRef, ActorSystem, Props, Stash}
import com.typesafe.config.{Config, ConfigFactory}
import config.ActorConfig.RemoteActorInfo
import config.Config.{enterCS, exitCS}
import messages.ChatBehaviour._

import scala.collection.mutable.ListBuffer

/**
  * @author Monica Gondolini
  */
class RemoteActor extends Actor with Stash{

  private var actorList: ListBuffer[ActorRef] = new ListBuffer[ActorRef]
  private var participants: ListBuffer[String] = new ListBuffer[String]
  private var CSuser: Option[String] = None
  private var cs: Boolean = false
  private var count: Int = 0

  override def receive: Receive = {
    case AddParticipant(username) => addToParticipantsList(sender, username)
    case MessageRequest(message, username, timestamp) => examineMessage(message, username, timestamp)
    case CSaccepted(username) =>
      for(_ <- actorList)
        count += 1

      if(count == actorList.size){
        cs = true
        setCSuser(Some(username))
      }
    case RemoveParticipant(username) =>
      removeFromParticipantsList(sender, username)
      sender ! ExitSuccess()
  }

  private def addToParticipantsList(actorIdentity: ActorRef, username: String){
    actorList += actorIdentity
    participants += username
    println("User in list (add option) -> " + actorInList + "-" + participants)
  }

  private def removeFromParticipantsList(actorIdentity: ActorRef, username: String) {
    actorList -= actorIdentity
    participants -= username
    println("User in list (remove option) -> " + actorInList + "-" + participants)
  }

  private def actorInList(): ListBuffer[ActorRef]  = actorList

  private def setCSuser(username: Option[String]): Unit = CSuser = username

  private def examineMessage(message: String, username: String, timestamp: String): Unit = {
    message match {
      case `enterCS` =>
        println(enterCS)
        actorList foreach{ actor => actor ! CSrequest(username)}
      case `exitCS` =>
        println(exitCS)
        count = 0
        cs = false
        setCSuser(None)
      case _ =>
        if (cs && CSuser.get.equals(username)) actorList foreach{actor => actor ! DispatchMessage(message, username, timestamp)}
        else if (!cs && CSuser.isEmpty) actorList foreach{actor => actor ! DispatchMessage(message, username, timestamp)}
        else stash()
    }
  }
}

object RemoteActor {
  def main(args: Array[String])  {
    val config: Config = ConfigFactory.load(RemoteActorInfo.Configuration)
    val system = ActorSystem(RemoteActorInfo.Context, config)
    system.actorOf(Props[RemoteActor], name=RemoteActorInfo.Name)
  }
}