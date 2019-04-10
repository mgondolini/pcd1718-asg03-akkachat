package messages

object ChatBehaviour {

  sealed trait ChatBehaviour

  case class AddParticipant(username: String) extends ChatBehaviour

  case class RemoveParticipant(username: String) extends ChatBehaviour

  case class Dispatch(message: String, username: String) extends ChatBehaviour

  case class MessageRequest(message: String, username: String) extends ChatBehaviour

  case class DispatchMessage(message: String, username: String) extends ChatBehaviour
}
