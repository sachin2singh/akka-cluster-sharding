package com.akka.example.actor;

import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import akka.cluster.sharding.typed.javadsl.EntityTypeKey;
import com.akka.example.CardRequest;
import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CardHandlerActor extends AbstractBehavior<CardHandlerActor.Command> {

    private final ActorContext<Command> actorContext;
    private final String entityId;
    private final String shardId;
    private final String memberId;
    public static EntityTypeKey<Command> entityTypeKey = EntityTypeKey.create(Command.class, CardHandlerActor.class.getSimpleName());

    public static Behavior<Command> create(String entityId) {
        return Behaviors.setup(actorContext -> new CardHandlerActor(actorContext, entityId));
    }

    private CardHandlerActor(ActorContext<Command> actorContext, String entityId) {
        super(actorContext);
        this.actorContext = actorContext;
        this.entityId = entityId;
        shardId = "" + Math.abs(entityId.hashCode()) % actorContext.getSystem().settings().config().getInt("akka.cluster.sharding.number-of-shards");
        memberId = actorContext.getSystem().address().toString();
        log.info("Start {}", entityId);
    }

    @Override
    public Receive<CardHandlerActor.Command> createReceive() {
        return newReceiveBuilder()
                .onMessage(CardHandlerActor.CardHandlerProcessor.class, this::processCard)
                .onMessage(CardHandlerActor.Passivate.class, msg->onPassivate())
                .build();
    }

    private Behavior<CardHandlerActor.Command>  processCard(CardHandlerActor.CardHandlerProcessor cardHandlerProcessor) {
        log.info("Calling sharded actor basis on entity and shared id");
        return this;
    }

    public interface Command extends CborSerializable {}

    public static class CardHandlerProcessor implements CardHandlerActor.Command {
        final CardRequest cardRequest;

        @JsonCreator
        public CardHandlerProcessor (CardRequest cardRequest) {
            this.cardRequest = cardRequest;
        }
    }

    private Behavior<Command> onPassivate() {
        log.info("Stop passivate {} {} {}", entityId, shardId, memberId);
        return Behaviors.stopped();
    }

    public enum Passivate implements CardHandlerActor.Command {
        INSTANCE;
    }
}
