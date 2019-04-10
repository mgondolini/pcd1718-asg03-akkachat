
import actor.AuthenticationActor
import akka.actor.Props
import config.ActorConfig.ActorSystemInfo.system
import config.Config.ViewConfig.mainView
import controller.Controller
import javafx.application.{Application, Platform}
import javafx.fxml.FXMLLoader
import javafx.scene.{Parent, Scene}
import javafx.stage.Stage

class ClientMain extends Application {

    override def start(primaryStage: Stage): Unit = {
      val loader = new FXMLLoader()
      system actorOf(Props(new AuthenticationActor(loader.getController.asInstanceOf[Controller])), "AuthenticationActor")

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