package controller

import akka.actor.ActorSelection
import config.ActorConfig.ActorPath.AuthenticationActorPath
import config.ActorConfig.ActorSystemInfo.system
import config.Config.ViewConfig.chatRoomView
import javafx.application.Platform
import javafx.fxml.FXML
import javafx.scene.control.Alert.AlertType.NONE
import javafx.scene.control._
import messages.ChatAuthentication.{EnterRoom, SetController}
import model.{User, ViewSwitch}


class Controller {

  @FXML var enterButton: Button = _
  @FXML var usernameField: TextField = _
  @FXML var chatNameField: TextField = _
  @FXML var roomsListView: ListView[String] = _

  val authenticationActor: ActorSelection = system actorSelection AuthenticationActorPath

  def initialize(): Unit =  authenticationActor ! SetController(this)

  @FXML def enterRoom(): Unit = {
    val user = User(usernameField.getText)
    user.username match {
      case "" => showDialog("Insert username")
      case _ => authenticationActor ! EnterRoom(user)
    }
  }

  def showDialog(text: String): Unit = {
    val alert: Alert = new Alert(NONE, text, ButtonType.OK)
    alert.showAndWait
  }

  def openChatView(): Unit = Platform.runLater(() => ViewSwitch(chatRoomView, enterButton.getScene).changeView())

  
}
