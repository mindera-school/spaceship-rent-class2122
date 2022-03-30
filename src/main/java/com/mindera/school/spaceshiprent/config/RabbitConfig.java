package com.mindera.school.spaceshiprent.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    @Bean // é pra fazer com q isso seja uma das primeiras coisas a ser instanciadas
    public Queue queue() {
        return new Queue("myQueue", true);
    }
}
