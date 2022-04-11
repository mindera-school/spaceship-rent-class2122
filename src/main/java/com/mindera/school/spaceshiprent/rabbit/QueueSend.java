package com.mindera.school.spaceshiprent.rabbit;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class QueueSend {
    private final RabbitTemplate rabbitTemplate;
    private Queue queue;

    public void send(String email) {
        rabbitTemplate.convertAndSend("spaceShipQueue", email);
    }
}

