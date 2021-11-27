package com.akka.example.rest;

import com.akka.example.AkkaService;
import com.akka.example.CardRequest;
import com.akka.example.CardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/akka/demo")
public class AkkaRestApi {

    @Autowired
    private AkkaService AkkaService;

    @PostMapping("/card")
    CardResponse newEmployee(@RequestBody CardRequest cardRequest) {
        return AkkaService.processRequest(cardRequest);
    }
}
