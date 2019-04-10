
import akka.actor.{Actor, ActorLogging, ActorRef, Props, Stash}
import config.ActorConfig.RemoteActorInfo.Name
import config.ActorConfig.RemoteSystemInfo.remoteSystem
import messages.ChatBehaviour.{AddParticipant, DispatchMessage, MessageRequest, RemoveParticipant}

import scala.collection.mutable.ListBuffer

class RemoteActor extends Actor with Stash with ActorLogging{

  private var actorList: ListBuffer[ActorRef] = new ListBuffer[ActorRef]
  private var participants: ListBuffer[String] = new ListBuffer[String]

  override def receive: Receive = {
    case AddParticipant(username) => addIntoParticipantsList(sender(), username)
    case MessageRequest(message, username) => actorList.foreach{ actor => actor ! DispatchMessage(message, username) }
    case RemoveParticipant(username) => removeFromParticipantsList(sender(), username)
    case _ => stash()
  }

  private def addIntoParticipantsList(actorIdentity: ActorRef, username: String){
    actorList += actorIdentity
    participants += username
    log.info("User in list (add option) -> " + actorInList)
  }

  private def removeFromParticipantsList(actorIdentity: ActorRef, username: String) {
    actorList -= actorIdentity
    participants -= username
    log.info("User in list (remove option) -> " + actorInList)
    unstashAll()
  }

  private def actorInList():  ListBuffer[ActorRef]  = actorList
}

object RemoteActor {
  def main(args: Array[String])  {
    remoteSystem.actorOf(Props[RemoteActor], name=Name)
    println("------ cml.RemoteActor is ready")
  }
}