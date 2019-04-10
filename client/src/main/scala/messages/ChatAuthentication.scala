package messages

import controller.Controller
import model.User

object ChatAuthentication {

  sealed trait ChatAuthentication

  case class EnterRoom(_user: User) extends ChatAuthentication

  case class UserRequest() extends ChatAuthentication

  case class SetController(controller: Controller) extends ChatAuthentication

}
