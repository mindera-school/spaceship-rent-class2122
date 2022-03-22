package com.mindera.school.spaceshiprent.messaging;

import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class QueueSender {

    private RabbitTemplate rabbitTemplate;

    public void sender(String message) {
        rabbitTemplate.convertAndSend("create-email",message);
    }

}
