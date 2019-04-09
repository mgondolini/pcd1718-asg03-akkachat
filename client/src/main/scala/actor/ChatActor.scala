package actor

import akka.actor.Actor
import controller.Controller
import messages.RoomsManagement._

class ChatActor(_controller: Controller) extends Actor{

  var chatController: Controller = _controller

  override def receive: Receive = roomManagement

  private def roomManagement: Receive = {
    case RoomsList() => ???
    case AddRoom(room) => ???
    case RemoveRoom(room) => ???
    case EnterRoom(username, room) => ???
    case SetController(controller) =>
      chatController = controller
      val selfActor = self
      selfActor ! RoomsList()
  }


}
