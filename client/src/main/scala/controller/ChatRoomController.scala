package controller

import akka.actor.ActorSelection
import config.ActorConfig.ActorSystemInfo.system
import config.ActorConfig.ActorPath.ChatRoomActorPath
import config.ViewConfig.mainView
import javafx.application.Platform
import javafx.fxml.FXML
import javafx.scene.control.Alert.AlertType.ERROR
import javafx.scene.control._
import messages.ChatBehaviour.{QuitChat, SendMessage, SetController}
import model.{User, ViewSwitch}


class ChatRoomController {

  @FXML var quitButton: Button = _
  @FXML var messageField: TextField = _
  @FXML var messagesArea: TextArea = _
  @FXML var chatRoomLabel: Label = _

  var user: User = _
  var room: String = _
  val chatRoomActor: ActorSelection = system actorSelection ChatRoomActorPath

  def initialize(): Unit = {
    chatRoomActor ! SetController(this)
  }

  @FXML def sendMessage(): Unit = {
    val message = messageField.getText
    message match {
      case "" =>
        val alert = new Alert(ERROR, "Cannot send an empty message", ButtonType.OK)
        alert.showAndWait
      case _ =>
        chatRoomActor ! SendMessage(message)
        messageField.clear()
    }
  }

  @FXML def quitChat(): Unit = chatRoomActor ! QuitChat()

  def setUser(_user: User): Unit = user = _user

  def setRoom(_room: String): Unit = room = _room

  def exitChatView(): Unit = Platform.runLater(() => ViewSwitch(mainView, quitButton.getScene).changeView())
}
