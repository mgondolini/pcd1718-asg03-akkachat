package controller

import actor.ChatRoomActor
import akka.actor.{ActorRef, ActorSelection, Props}
import config.ActorConfig.LocalSystemInfo.localSystem
import config.ActorConfig.ActorPath.ChatRoomActorPath
import config.ActorConfig.ChatRoomActorInfo.Name
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

  var chatRoomActor: ActorRef = localSystem actorOf(Props[ChatRoomActor], name=Name)

  var user: User = _

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
        chatRoomActor ! SendMessage(message, user.username)
        messageField.clear()
    }
  }

  @FXML def quitChat(): Unit = chatRoomActor ! QuitChat()

  def setUser(_user: User): Unit = user = _user

  def exitChatView(): Unit = Platform.runLater(() => ViewSwitch(mainView, quitButton.getScene).changeView())
}
