package controller

import java.util.Optional

import akka.actor.ActorSelection
import config.ActorConfig.ActorPath.ChatActorPath
import config.ActorConfig.ActorSystemInfo.system
import config.ViewConfig.chatRoomView
import javafx.application.Platform
import javafx.collections.{FXCollections, ObservableList}
import javafx.fxml.FXML
import javafx.scene.control.Alert.AlertType.NONE
import javafx.scene.control._
import messages.RoomsManagement.{AddRoom, RemoveRoom, RoomsList, SetController}
import model.{User, ViewSwitch}


class Controller {

  @FXML var enterButton: Button = _
  @FXML var usernameField: TextField = _
  @FXML var chatNameField: TextField = _
  @FXML var roomsListView: ListView[String] = _

  var obsList: ObservableList[String] = FXCollections.observableArrayList()
  val chatActor: ActorSelection = system actorSelection ChatActorPath

  def initialize(): Unit = {
    chatActor ! SetController(this)
  }

  @FXML def addRoom(): Unit = {
    if (chatNameField.getText.isEmpty) showDialog("Empty room field.")
    else chatActor ! AddRoom(getRoomName.get)
  }

  @FXML def removeRoom(): Unit = {
    if (chatNameField.getText.isEmpty) showDialog("Empty room field.")
    else chatActor ! RemoveRoom(getRoomName.get)
  }

  @FXML def enterRoom(): Unit = {

    val selection = roomsListView.getSelectionModel.getSelectedItem
    val user = User(usernameField.getText)
    val viewSwitch = ViewSwitch(chatRoomView,  enterButton.getScene)

    if (roomsListView.getSelectionModel.selectedItemProperty.isNull.get) showDialog("Select room")
    else if (user.username == "") showDialog("Insert username")
    else Platform.runLater(() => viewSwitch.changeToRoomView(user, selection))
  }

  def showDialog(text: String): Unit = {
    val alert: Alert = new Alert(NONE, text, ButtonType.OK)
    alert.showAndWait
  }

  private def getRoomName: Optional[String] = Optional.of(chatNameField.getText)
}
