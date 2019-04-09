package messages

import controller.Controller

object ChatManager {

  sealed trait ChatManager

  case class EnterRoom(username:String) extends ChatManager

  case class SetController(controller: Controller) extends ChatManager

}
