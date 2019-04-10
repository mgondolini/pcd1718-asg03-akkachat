package messages

import controller.ChatRoomController
import model.User

object ChatBehaviour {

  sealed trait ChatBehaviour

  case class SetUser(user: User) extends ChatBehaviour

  case class SendMessage(message: String, username: String) extends ChatBehaviour

  case class Participant(username: String) extends ChatBehaviour

  case class QuitChat() extends ChatBehaviour

  case class SetController(controller: ChatRoomController) extends ChatBehaviour
}
