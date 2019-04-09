package controller

import javafx.fxml.FXML
import javafx.scene.control.{Button, Label, TextArea, TextField}

class ChatRoomController {

  @FXML var quitButton: Button = _
  @FXML var messageField: TextField = _
  @FXML var messagesArea: TextArea = _
  @FXML var chatRoomLabel: Label = _

  def initialize(): Unit = ???

  @FXML def sendMessage(): Unit = ???

  @FXML def quitChat(): Unit = ???
}
