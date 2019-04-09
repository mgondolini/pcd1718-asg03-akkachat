package controller

import akka.actor.ActorSelection
import javafx.fxml.FXML
import javafx.scene.control.{Button, ListView, TextField}

import config.ActorConfig.ActorSystemInfo.system
import config.ActorConfig.ActorPath.ChatActorPath

class Controller {

  @FXML var enterButton: Button = _
  @FXML var usernameField: TextField = _
  @FXML var chatNameField: TextField = _
  @FXML var roomsListView: ListView[String] = _

  val chatActor: ActorSelection = system actorSelection ChatActorPath

  def initialize(): Unit = ???

  @FXML def addRoom(): Unit = ???

  @FXML def removeRoom(): Unit = ???

  @FXML def enterRoom(): Unit = ???
}
