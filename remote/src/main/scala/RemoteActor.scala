
import akka.actor.{Actor, ActorLogging, ActorRef, ActorSystem, Props, Stash}
import com.typesafe.config.{Config, ConfigFactory}
import config.ActorConfig.RemoteActorInfo
import config.ActorConfig.RemoteActorInfo.Name
import messages.ChatBehaviour._

import scala.collection.mutable.ListBuffer

class RemoteActor extends Actor with Stash with ActorLogging{

  private var actorList: ListBuffer[ActorRef] = new ListBuffer[ActorRef]
  private var participants: ListBuffer[String] = new ListBuffer[String]

  override def receive: Receive = {
    case AddParticipant(username) => addIntoParticipantsList(sender, username)
    case MessageRequest(message, username) => actorList.foreach{ actor => actor ! DispatchMessage(message, username) }
    case RemoveParticipant(username) =>
      removeFromParticipantsList(sender, username)
      sender ! ExitSuccess()
    case _ => stash()
  }

  private def addIntoParticipantsList(actorIdentity: ActorRef, username: String){
    actorList += actorIdentity
    participants += username
    log.info("User in list (add option) -> " + actorInList + "-" + participants)
  }

  private def removeFromParticipantsList(actorIdentity: ActorRef, username: String) {
    actorList -= actorIdentity
    participants -= username
    log.info("User in list (remove option) -> " + actorInList+ "-" + participants)
    unstashAll()
  }

  private def actorInList():  ListBuffer[ActorRef]  = actorList

}

object RemoteActor {
  def main(args: Array[String])  {
    val config: Config = ConfigFactory.load(RemoteActorInfo.Configuration)
    val system = ActorSystem(RemoteActorInfo.Context, config)
    system.actorOf(Props[RemoteActor], name=Name)
    println("------ cml.RemoteActor is ready")
  }
}