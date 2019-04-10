package actor

import akka.actor.Actor
import controller.Controller
import messages.ChatAuthentication.{EnterRoom, SetController, UserRequest}
import messages.ChatBehaviour.SetUser
import model.User

class AuthenticationActor(_controller: Controller) extends Actor{

  var chatController: Controller = _controller

  var user: User = _

  override def receive: Receive = roomManagement

  private def roomManagement: Receive = {
    case EnterRoom(_user) =>
      chatController.openChatView()
      user = _user
    case UserRequest() => sender ! SetUser(user)
    case SetController(controller) => chatController = controller
  }

}
