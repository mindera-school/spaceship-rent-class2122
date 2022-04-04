package com.mindera.school.spaceshiprent.Rabbit;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;




@Component
public class RabbitConfiguration {

    @Bean
    public Queue queue(){
        return new Queue("spaceShipQueue", true);
    }
}
