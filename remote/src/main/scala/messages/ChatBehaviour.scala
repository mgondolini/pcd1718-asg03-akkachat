package messages

/**
  * @author Monica Gondolini
  */
object ChatBehaviour {

  sealed trait ChatBehaviour

  case class AddParticipant(username: String) extends ChatBehaviour

  case class RemoveParticipant(username: String) extends ChatBehaviour

  case class Dispatch(message: String, username: String) extends ChatBehaviour

  case class MessageRequest(message: String, username: String) extends ChatBehaviour

  case class DispatchMessage(message: String, username: String) extends ChatBehaviour

  case class ExitSuccess() extends ChatBehaviour

  case class CSrequest(username: String) extends ChatBehaviour

  case class CSaccepted(username: String) extends ChatBehaviour

}
