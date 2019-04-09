package actor

import akka.actor.{Actor, ActorRef, Props}
import controller.Controller
import messages.ChatAuthentication.{EnterRoom, UserRequest, SetController}
import messages.ChatBehaviour.SetUser
import model.User

class ChatActor(_controller: Controller) extends Actor{

  var chatController: Controller = _controller
  var chatRoomActor: ActorRef = context.system.actorOf(Props[ChatRoomActor], "ChatRoomActor")
  var user: User = _

  override def receive: Receive = roomManagement

  private def roomManagement: Receive = {
    case EnterRoom(_user) =>
      chatController.openChatView()
      user = _user
    case UserRequest() => chatRoomActor ! SetUser(user)
    case SetController(controller) => chatController = controller
  }

}
