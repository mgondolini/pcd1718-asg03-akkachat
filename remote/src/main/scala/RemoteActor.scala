
import akka.actor.{Actor, ActorLogging, ActorRef, ActorSystem, Props, Stash}
import messages.ChatBehaviour.{AddParticipant, DispatchMessage, MessageRequest, RemoveParticipant}
import config.ActorConfig.RemoteActorInfo.{Configuration,Context, Name}

import scala.collection.mutable.ListBuffer

class RemoteActor extends Actor with Stash with ActorLogging{

  private var actorList: ListBuffer[ActorRef] = new ListBuffer[ActorRef]
  private var participants: ListBuffer[String] = new ListBuffer[String]

  override def receive: Receive = {
    case AddParticipant(username) => addIntoParticipantsList(sender, username)
    case MessageRequest(message, username) => actorList.foreach{ actor => actor ! DispatchMessage(message, username) }
    case RemoveParticipant(username) => removeFromParticipantsList(self, username)
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
    import com.typesafe.config.ConfigFactory
    val config = ConfigFactory.load(Configuration)
    val system = ActorSystem(Context, config)
    system.actorOf(Props[RemoteActor], name=Name)
    println("------ cml.RemoteActor is ready")
  }
}