package controller

import akka.actor.ActorSelection
import config.ActorConfig.ActorPath.ChatActorPath
import config.ActorConfig.ActorSystemInfo.system
import config.ViewConfig.chatRoomView
import javafx.application.Platform
import javafx.fxml.FXML
import javafx.scene.control.Alert.AlertType.NONE
import javafx.scene.control._
import messages.ChatManager.{EnterRoom, SetController}
import model.{User, ViewSwitch}


class Controller {

  @FXML var enterButton: Button = _
  @FXML var usernameField: TextField = _
  @FXML var chatNameField: TextField = _
  @FXML var roomsListView: ListView[String] = _

  val chatActor: ActorSelection = system actorSelection ChatActorPath

  def initialize(): Unit =  chatActor ! SetController(this)

  @FXML def enterRoom(): Unit = {
    val username = User(usernameField.getText).username
    username match {
      case "" => showDialog("Insert username")
      case _ => chatActor ! EnterRoom(username)
    }
  }

  def showDialog(text: String): Unit = {
    val alert: Alert = new Alert(NONE, text, ButtonType.OK)
    alert.showAndWait
  }

  def openChatView(): Unit = Platform.runLater(() => ViewSwitch(chatRoomView, enterButton.getScene).changeView())
}
