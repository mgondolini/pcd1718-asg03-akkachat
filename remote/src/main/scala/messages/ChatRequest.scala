package messages

/**
  * @author Monica Gondolini
  */
object ChatRequest {

  sealed trait ChatRequest

  case class AddParticipant() extends ChatRequest

  case class RemoveParticipant(username: String) extends ChatRequest

  case class MessageRequest(message: String, username: String, timestamp: String) extends ChatRequest

  case class CSrequest(username: String) extends ChatRequest

}
