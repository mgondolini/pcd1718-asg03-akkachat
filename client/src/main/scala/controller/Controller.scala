package controller

import javafx.fxml.FXML
import javafx.scene.control.{Button, ListView, TextField}

class Controller {

  @FXML var enterButton: Button = _
  @FXML var usernameField: TextField = _
  @FXML var chatNameField: TextField = _
  @FXML var roomsListView: ListView[String] = _

  def initialize(): Unit = ???

  @FXML def addRoom(): Unit = ???

  @FXML def removeRoom(): Unit = ???

  @FXML def enterRoom(): Unit = ???
}
