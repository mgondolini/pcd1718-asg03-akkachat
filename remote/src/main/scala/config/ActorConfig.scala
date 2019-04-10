package config

object ActorConfig {

  object RemoteActorInfo{
    val Configuration = "remote_actor.conf"
    val Context = "akkachat"
    val Name = "RemoteActor"
    val Path = "akka.tcp://akkachat@127.0.0.1:5051/user/RemoteActor"
  }





}

