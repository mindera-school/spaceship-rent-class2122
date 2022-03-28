package com.mindera.school.spaceshiprent.components.messages;

import com.mindera.school.spaceshiprent.dto.user.UserDetailsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MessageSender {

    private final RabbitTemplate RABBITTEMPLATE;

    public void sendUserCreated(String userDetailsDto){
        RABBITTEMPLATE.convertAndSend("email", userDetailsDto.toString());
    }
}

