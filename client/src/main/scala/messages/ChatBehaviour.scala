package messages

import controller.ChatRoomController
import model.User

object ChatBehaviour {

  sealed trait ChatBehaviour

  case class SetUser(user: User) extends ChatBehaviour

  case class SendMessage(message: String, username: String) extends ChatBehaviour

  case class AddParticipant(username: String) extends ChatBehaviour

  case class RemoveParticipant(username: String) extends ChatBehaviour

  case class Dispatch(message: String, username: String) extends ChatBehaviour

  case class MessageRequest(message: String, username: String) extends ChatBehaviour

  case class DispatchMessage(message: String, username: String) extends ChatBehaviour

  case class QuitChat() extends ChatBehaviour

  case class ExitSuccess() extends ChatBehaviour

  case class SetController(controller: ChatRoomController) extends ChatBehaviour
}
