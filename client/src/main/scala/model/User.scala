package model

/**
  * @author Monica Gondolini
  */
trait Credentials{
  def username: String
  def setUsername(username: String): Unit
}

case class User(var _username: String) extends Credentials{
  override def username: String = _username
  override def setUsername(username:String): Unit = _username = username
}
