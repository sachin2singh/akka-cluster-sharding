package com.akka.example.rest;

import com.akka.example.AkkaService;
import com.akka.example.CardRequest;
import com.akka.example.CardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/akka/demo")
public class AkkaRestApi {

    @Autowired
    private AkkaService AkkaService;

    @PostMapping("/card")
    CardResponse newEmployee(@RequestBody CardRequest cardRequest) {
        return AkkaService.processRequest(cardRequest);
    }

    @GetMapping ("/details")
    CardResponse cardDetails() {
        CardRequest cardRequest = new CardRequest();
        return AkkaService.processRequest(cardRequest);
    }
}
