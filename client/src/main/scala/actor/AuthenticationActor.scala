package actor

import akka.actor.Actor
import controller.Controller
import messages.ChatAuthentication.{EnterRoom, SetController, UserRequest}
import messages.ChatRequest.SetUser
import model.User

/**
  * @author Monica Gondolini
  */
class AuthenticationActor(_controller: Controller) extends Actor{

  var authenticationController: Controller = _controller
  var user: User = _

  override def receive: Receive = {
    case EnterRoom(_user) =>
      authenticationController.openChatView()
      user = _user
    case UserRequest() => sender ! SetUser(user)
    case SetController(controller) => authenticationController = controller
  }

}
