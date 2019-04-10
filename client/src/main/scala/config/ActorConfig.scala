package config

import akka.actor.ActorSystem

object ActorConfig {

  object ActorSystemInfo {
    val system: ActorSystem = ActorSystem("akkachat")
  }

  object RemoteActorInfo{
    val Path = "akka.tcp://akkachat@127.0.0.1:5051/user/RemoteActor"
  }

  object ChatRoomActorInfo{
    val Configuration = "actor/local_actor.conf"
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

