package actor

import akka.actor.{Actor, ActorRef, Props}
import controller.Controller
import messages.ChatManager._

class ChatActor(_controller: Controller) extends Actor{

  var chatController: Controller = _controller
  var chatRoomActor: ActorRef = context.system.actorOf(Props[ChatRoomActor], "ChatRoomActor");

  override def receive: Receive = roomManagement

  private def roomManagement: Receive = {
    case EnterRoom(username) =>
     chatController.openChatView()
//      chatRoomActor ! SetUsername(username)
    //mandare msg per settare username chatroom controller
    case SetController(controller) => chatController = controller
  }

}
