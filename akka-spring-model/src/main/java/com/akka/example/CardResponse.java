package com.akka.example;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CardResponse {
    private String status;
    private String message;

    public CardResponse (String status, String message) {
        this.status = status;
        this.message = message;

    }
}
