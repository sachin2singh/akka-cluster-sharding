package com.akka.example;

import akka.cluster.sharding.typed.javadsl.EntityRef;
import com.akka.example.actor.CardHandlerActor;
import com.akka.example.config.AkkaManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.akka.example.util.AkkaActorUtil.entityId;

@Service
public class AkkaService {

    @Autowired
    private AkkaManager akkaManager;

    public CardResponse processRequest (CardRequest cardRequest) {

        CardResponse cardResponse = new CardResponse ("success", "card data process successfully");
        processCard(cardRequest);
        return cardResponse;
    }

    private void processCard(CardRequest cardRequest) {

        clusterShardActorRef ().tell(new CardHandlerActor.CardHandlerProcessor(cardRequest));
    }

    public EntityRef clusterShardActorRef () {
        final var entityId = entityId(akkaManager.getNodePort(), (int)Math.round(Math.random()* akkaManager.getEntitiesPerNode()));
        EntityRef entityRef = akkaManager.getClusterSharding().entityRefFor(CardHandlerActor.entityTypeKey, entityId);
        return entityRef;
    }
}
