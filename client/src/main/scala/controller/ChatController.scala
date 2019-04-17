package controller

import actor.ChatActor
import akka.actor.{ActorRef, ActorSystem, Props}
import com.typesafe.config.{Config, ConfigFactory}
import config.ActorConfig.ChatActorInfo
import config.Config.ViewConfig.mainView
import config.Config.CSconfig.CScommands
import javafx.application.Platform
import javafx.fxml.FXML
import javafx.scene.control.Alert.AlertType.ERROR
import javafx.scene.control._
import javafx.scene.layout.AnchorPane
import javafx.stage.Stage
import messages.ChatRequest.{QuitChat, SendMessage, SetController}
import model.{User, ViewSwitch}

/**
  * @author Monica Gondolini
  */
class ChatController {

  @FXML var anchorPane: AnchorPane = _
  @FXML var quitButton: Button = _
  @FXML var messageField: TextField = _
  @FXML var messagesArea: TextArea = _
  @FXML var chatRoomLabel: Label = _

  val config: Config = ConfigFactory.load(ChatActorInfo.Configuration)
  val localSystem = ActorSystem(ChatActorInfo.Context, config)
  var chatActor: ActorRef = localSystem actorOf(Props[ChatActor], name = ChatActorInfo.Name)

  var user: User = _

  def initialize(): Unit = {
    chatActor ! SetController(this)
    displayMessage(CScommands)
    Platform.runLater(() => {
      val stage: Stage = anchorPane.getScene.getWindow.asInstanceOf[Stage]
      closeStage(stage)
    })
  }

  @FXML def sendMessage(): Unit = {
    val message = messageField.getText
    message match {
      case "" =>
        val alert = new Alert(ERROR, "Cannot send an empty message", ButtonType.OK)
        alert.showAndWait
      case _ =>
        chatActor ! SendMessage(message, user.username)
        messageField.clear()
    }
  }

  @FXML def quitChat(): Unit = chatActor ! QuitChat()

  def setUser(_user: User): Unit = user = _user

  def exitChatView(): Unit = Platform.runLater(() => ViewSwitch(mainView, quitButton.getScene).changeView())

  def displayMessage(msg: String): Unit = messagesArea.appendText(msg + "\n")

  private def closeStage(stage: Stage): Unit = {
    stage.setOnCloseRequest(_ =>{
      chatActor ! QuitChat()
      Platform.exit()
      System.exit(0)
    })
  }
}
