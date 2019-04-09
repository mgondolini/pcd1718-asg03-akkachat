package actor

import akka.actor.{Actor, ActorSelection}
import config.ActorConfig.ActorPath.ChatActorPath
import config.ActorConfig.ActorSystemInfo.system
import controller.ChatRoomController

class ChatRoomActor(chatRoomController: ChatRoomController) extends Actor {

  val chatActor: ActorSelection = system actorSelection ChatActorPath

  override def receive: Receive = chatBehaviour

  private def chatBehaviour: Receive = ???

}
