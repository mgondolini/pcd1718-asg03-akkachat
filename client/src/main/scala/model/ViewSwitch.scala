package model

import javafx.fxml.FXMLLoader
import javafx.scene.Scene

/**
  * @author Monica Gondolini
  */

trait Switch{
  def changeView(): Unit
}

case class ViewSwitch(view: String, scene: Scene) extends Switch {
  override def changeView(): Unit = {
    scene.setRoot(FXMLLoader.load(getClass.getClassLoader.getResource(view)))
    scene.getWindow.sizeToScene()
  }
}
