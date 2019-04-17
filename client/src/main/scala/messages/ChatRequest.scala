package messages

import controller.ChatController
import model.User

/**
  * @author Monica Gondolini
  */

object ChatRequest {

  sealed trait ChatRequest

  case class SetUser(user: User) extends ChatRequest

  case class SendMessage(message: String, username: String) extends ChatRequest

  case class AddParticipant() extends ChatRequest

  case class RemoveParticipant(username: String) extends ChatRequest

  case class MessageRequest(message: String, username: String, timestamp: String) extends ChatRequest

  case class QuitChat() extends ChatRequest

  case class CSrequest(username: String) extends ChatRequest

  case class SetController(controller: ChatController) extends ChatRequest
}
