package com.akka.example.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class AkkaConfig {

    @Value("${akka.initializer.config.file.name}")
    private String akkaConfigFileName;

    @Value("${akka.initializer.actor.system.name:AkkaInitializerActorSystem}")
    private String actorSystemName;
}
