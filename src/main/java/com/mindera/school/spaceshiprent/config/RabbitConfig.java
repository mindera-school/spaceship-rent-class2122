package com.mindera.school.spaceshiprent.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitConfig {

    @Bean
    Queue registeredClientQueue() {
        return new Queue("emailQueue", true);
    }
}
