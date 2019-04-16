package actor

import akka.actor.Actor
import controller.Controller
import messages.ChatAuthentication.{EnterRoom, SetController, UserRequest}
import messages.ChatBehaviour.SetUser
import model.User

/**
  * @author Monica Gondolini
  */
class AuthenticationActor(_controller: Controller) extends Actor{

  var chatController: Controller = _controller
  var user: User = _

  override def receive: Receive = {
    case EnterRoom(_user) =>
      chatController.openChatView()
      user = _user
    case UserRequest() => sender ! SetUser(user)
    case SetController(controller) => chatController = controller
  }

}
