package com.akka.example;

import org.springframework.stereotype.Service;

@Service
public class AkkaService {

    public CardResponse processRequest (CardRequest cardRequest) {

        CardResponse cardResponse = new CardResponse ("success", "card data process successfully");
        return cardResponse;
    }
}
