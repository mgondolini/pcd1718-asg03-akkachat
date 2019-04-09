package model

import javafx.fxml.FXMLLoader
import javafx.scene.Scene

trait Switch{
  def changeView(): Unit
  def changeToRoomView(user: User, room: String): Unit
}

case class ViewSwitch(view: String, scene: Scene) extends Switch {

  override def changeView(): Unit = {
    scene.setRoot(FXMLLoader.load(getClass.getClassLoader.getResource(view)))
    scene.getWindow.sizeToScene()
  }

  override def changeToRoomView(user: User, room: String): Unit = {
    val loader = new FXMLLoader(getClass.getClassLoader.getResource(view))
    val anchorPane = loader.load
    val controller = loader.getController
//    controller.setRoom(room)
//    controller.setUser(user)
    scene.setRoot(anchorPane)
    scene.getWindow.sizeToScene()
  }
}
