package com.mindera.school.spaceshiprent.components;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailSender {

    private final RabbitTemplate rabbitTemplate;

    public void send(String email, String name) {
        String emailingInfo = email + " " + name;
        rabbitTemplate.convertAndSend("emailQueue", emailingInfo);
    }

}
