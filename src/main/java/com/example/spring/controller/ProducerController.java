package com.example.spring.controller;

import com.example.spring.controller.request.ProducerRequest;
import com.example.spring.service.ProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class ProducerController {

    @Autowired
    ProducerService producerService;

    @PostMapping
    void  produce(@RequestBody ProducerRequest request) {
        producerService.sendProduceRequestToAmqpQueue(request);
    }

}
