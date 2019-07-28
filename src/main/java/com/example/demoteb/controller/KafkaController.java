package com.example.demoteb.controller;

import com.example.demoteb.service.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KafkaController {

    @Autowired
    private Producer producer;

    @GetMapping("/producer/{message}")
    public void sendMessage(@PathVariable("message") String message){
        producer.sendMessage(message);
    }
}
