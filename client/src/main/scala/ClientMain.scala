
import actor.ChatActor
import akka.actor.Props
import javafx.application.{Application, Platform}
import javafx.fxml.FXMLLoader
import javafx.scene.{Parent, Scene}
import javafx.stage.Stage
import config.ViewConfig.mainView
import controller.Controller
import config.ActorConfig.ActorSystemInfo.system

class ClientMain extends Application {

    override def start(primaryStage: Stage): Unit = {
      val loader = new FXMLLoader()
      system actorOf(Props(new ChatActor(loader.getController.asInstanceOf[Controller])), "ChatActor")

      val root: Parent = loader.load(getClass.getClassLoader.getResource(mainView).openStream())
      val scene : Scene = new Scene(root)
      primaryStage.setTitle("Akka Chat")
      primaryStage.setScene(scene)
      primaryStage.setResizable(false)
      primaryStage.setOnCloseRequest(_ => {
        Platform.exit()
        System.exit(0)
      })
      primaryStage.show()
    }
}