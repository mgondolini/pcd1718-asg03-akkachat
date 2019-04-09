import akka.actor.{Actor, ActorRef, Stash}

import scala.collection.mutable.ListBuffer

class RemoteActor extends Actor with Stash{


  private var actorList: ListBuffer[ActorRef] = new ListBuffer[ActorRef]

  override def receive: Receive = ???
}
