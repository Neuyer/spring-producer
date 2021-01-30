package com.example.spring.service;

import com.example.spring.controller.request.ProducerRequest;

public interface ProducerService {

    void sendProduceRequestToAmqpQueue(ProducerRequest request);

    void consumeAmqpQueue(ProducerRequest request);

}
