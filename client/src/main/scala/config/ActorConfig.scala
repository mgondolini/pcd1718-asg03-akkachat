package config

import akka.actor.ActorSystem

object ActorConfig {

  object ActorSystemInfo {
    val system: ActorSystem = ActorSystem("akkachat")
  }

  object RemoteActorInfo {
    val Path = "akka.tcp://akkachat@127.0.0.1:5051/user/RemoteActor"
  }

  object ChatActorInfo {
    val Configuration = "actor/local_actor.conf"
    val Context = "akkachat"
    val Name = "ChatActor"
    val Path = "akka.tcp://akkachat@127.0.0.1:5051/user/ChatActor"
  }

  object ActorPath {
    val root = "akka://akkachat"
    val AuthenticationActorPath: String = root + "/user/AuthenticationActor"
    val ChatActorPath: String = root + "/user/ChatActor"
  }

}

