package com.mindera.school.spaceshiprent.rabbit;


import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class RabbitConfiguration {

    @Bean
    public Queue queue() {
        return new Queue("spaceShipQueue", true);
    }
}
