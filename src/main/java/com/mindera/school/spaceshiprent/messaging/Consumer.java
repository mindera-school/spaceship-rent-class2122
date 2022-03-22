package com.mindera.school.spaceshiprent.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class Consumer {

    private final Logger LOGGER = LoggerFactory.getLogger(Consumer.class);

    @RabbitListener(queues =  "create-email")
    private void receive(@Payload  String message) {
        LOGGER.info("Received message: {}", message);
    }


}
