package com.mindera.school.spaceshiprent.messaging;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Consumer {
    @RabbitListener(queues =  "create-email")
    private void receive(@Payload  String email) {
        log.info("Received message: {}", email);

    }


}
