package com.mindera.school.spaceshiprent;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;


//@EnableRabbit
@SpringBootApplication
public class SpaceshipRentApplication {

    public static void main(final String[] args) {
        SpringApplication.run(SpaceshipRentApplication.class, args);
    }

}
