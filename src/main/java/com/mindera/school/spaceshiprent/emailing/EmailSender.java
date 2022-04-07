package com.mindera.school.spaceshiprent.emailing;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailSender {

    private final RabbitTemplate rabbitTemplate;

    public void send(String emailingInfo) {
        rabbitTemplate.convertAndSend("email", emailingInfo);
    }

}
