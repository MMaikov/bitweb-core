package com.example.bitwebcore;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQSender {

    private final RabbitTemplate rabbitTemplate;
    private final String queueName;

    public RabbitMQSender(RabbitTemplate rabbitTemplate, @Value("${app.rabbitmq.queue:textQueue}") String queueName) {
        this.rabbitTemplate = rabbitTemplate;
        this.queueName = queueName;
    }

    public void send(TextUploadMessage message) {
        rabbitTemplate.convertAndSend(queueName, message);
    }
}
