package messages

/**
  * @author Monica Gondolini
  */

object ChatResponse {

  sealed trait ChatResponse

  case class DispatchMessage(message: String, username: String, timestamp: String) extends ChatResponse

  case class CSaccepted(username: String) extends ChatResponse

  case class ExitSuccess() extends ChatResponse
}
