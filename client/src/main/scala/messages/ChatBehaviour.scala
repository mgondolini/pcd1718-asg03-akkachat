package messages

import controller.ChatRoomController

object ChatBehaviour {

  sealed trait ChatBehaviour

  case class SendMessage(message: String) extends ChatBehaviour

  case class QuitChat() extends ChatBehaviour

  case class SetController(controller: ChatRoomController) extends ChatBehaviour
}
