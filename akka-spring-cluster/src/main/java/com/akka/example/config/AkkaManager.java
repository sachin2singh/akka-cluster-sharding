package com.akka.example.config;

import akka.actor.typed.ActorSystem;
import akka.actor.typed.Behavior;
import akka.actor.typed.Terminated;
import akka.actor.typed.javadsl.Behaviors;
import akka.cluster.sharding.typed.javadsl.ClusterSharding;
import akka.cluster.sharding.typed.javadsl.Entity;
import com.akka.example.actor.AccountHandlerActor;
import com.akka.example.actor.CardHandlerActor;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component("akkaManager")
@Slf4j
public class AkkaManager {

    @Autowired
    private AkkaConfig akkaConfig;
    private ActorSystem actorSystem;
    private ClusterSharding clusterSharding;
    private Integer nodePort;
    private int entitiesPerNode;

    @PostConstruct
    public void postConstruct () {
        log.debug("actorSystemName", akkaConfig.getActorSystemName());
        final var port = "2551";
        actorSystem = ActorSystem.create(create(), akkaConfig.getActorSystemName(), setupClusterNodeConfig(port));
    }

    Behavior<Void> create() {
        return Behaviors.setup(context -> {
            startClusterSharding(context.getSystem());

            return Behaviors.receive(Void.class)
                    .onSignal(Terminated.class, signal -> Behaviors.stopped())
                    .build();
        });
    }

    private Config setupClusterNodeConfig(String port) {
        final var config = ConfigFactory.load();

        final var hostname = config.getString("hostname");
        return ConfigFactory
                .parseString(String.format("akka.remote.artery.canonical.hostname = \"%s\"%n", hostname)
                        + String.format("akka.remote.artery.canonical.port=%s%n", port)
                        + String.format("akka.management.http.hostname = \"%s\"%n", "127.0.0.1")
                        + String.format("akka.management.http.port=%s%n", port.replace("255", "855"))
                        + String.format("akka.management.http.route-providers-read-only = %s%n", "false")
                        + String.format("akka.remote.artery.advanced.tcp.outbound-client-hostname = %s%n", hostname))
                .withFallback(config);
    }

    private void startClusterSharding(final ActorSystem<?> actorSystem) {
        final var clusterSharding = ClusterSharding.get(actorSystem);
        clusterSharding.init(
                Entity.of(
                                CardHandlerActor.entityTypeKey,
                                entityContext ->
                                        CardHandlerActor.create(entityContext.getEntityId())
                        )
                        .withStopMessage(CardHandlerActor.Passivate.INSTANCE)
        );

        clusterSharding.init(
                Entity.of(
                                AccountHandlerActor.entityTypeKey,
                                entityContext ->
                                        AccountHandlerActor.create(entityContext.getEntityId())
                        )
                        .withStopMessage(AccountHandlerActor.Passivate.INSTANCE)
        );
    }
}
