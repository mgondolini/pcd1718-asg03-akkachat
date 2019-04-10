package config

import akka.actor.ActorSystem
import com.typesafe.config.{Config, ConfigFactory}
import config.ActorConfig.ChatRoomActorInfo.Configuration
import config.ActorConfig.RemoteActorInfo.{Configuration, Context}


object ActorConfig {

  object ActorSystemInfo {
    val system: ActorSystem = ActorSystem("akkachat")
  }

  object RemoteActorInfo{
    val Configuration = "remote_actor.conf"
    val Context = "akkachat"
    val Name = "RemoteActor"
    val Path = "akka.tcp://akkachat@127.0.0.1:5051/user/RemoteActor"
  }

  object ChatRoomActorInfo{
    val Configuration = "local_actor.conf"
    val Context = "akkachat"
    val Name = "ChatRoomActor"
    val Path = "akka.tcp://akkachat@127.0.0.1:5051/user/ChatRoomActor"
  }

  object ActorPath {
    val root = "akka://akkachat"
    val AuthenticationActorPath: String = root + "/user/AuthenticationActor"
    val ChatRoomActorPath: String = root + "/user/ChatRoomActor"

  }

}

