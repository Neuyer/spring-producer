package com.example.spring.service;

import com.example.spring.controller.request.ProducerRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ProducerServiceImpl implements ProducerService {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Value("${spring.rabbitmq.request.routing-key.producer}")
    private String queue;

    @Value("${spring.rabbitmq.request.exchange.producer}")
    private String exchange;

    Logger logger = LoggerFactory.getLogger(ProducerServiceImpl.class);

    @Override
    public void sendProduceRequestToAmqpQueue(ProducerRequest request) {
            try {
                rabbitTemplate.convertAndSend(exchange, queue, request);
            } catch (AmqpException e) {
                throw new AmqpRejectAndDontRequeueException(e.getMessage());
            }
    }

    // consome tanto a queue principal quanto a deadletter
    @Override
    @RabbitListener(queues = {"${spring.rabbitmq.request.routing-key.producer}", "${spring.rabbitmq.request.deadletter.producer}"})
    public void consumeAmqpQueue(ProducerRequest request) {
        try {
                // se der qualquer erro na hora de consumir joga pra deadLetter
                logger.info(String.format("resposta da fila: %s ", request));
        }  catch (Exception e) {
            throw new AmqpRejectAndDontRequeueException(e.getMessage());
        }
    }
}
