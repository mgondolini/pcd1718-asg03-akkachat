package model

trait UserTrait{
  def username: String
  def setUsername(username: String): Unit
}

case class User(var _username: String) extends UserTrait{
  override def username: String = _username
  override def setUsername(username:String): Unit = _username = username
}
