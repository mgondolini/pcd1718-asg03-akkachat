package messages

import controller.Controller

object RoomsManagement {

  sealed trait RoomsManagement

  case class AddRoom(room: String) extends RoomsManagement

  case class RemoveRoom(room: String) extends RoomsManagement

  case class EnterRoom(username:String, room: String) extends RoomsManagement

  case class SetController(controller: Controller) extends RoomsManagement

}
