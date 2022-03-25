package com.mindera.school.spaceshiprent.messaging;

import com.mindera.school.spaceshiprent.dto.user.CreateOrUpdateUserDto;
import com.mindera.school.spaceshiprent.service.emailService.EmailServiceImpl;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class QueueSender {

    private final RabbitTemplate rabbitTemplate;
    private final EmailServiceImpl emailService;

    public void sender(String email) {
        rabbitTemplate.convertAndSend("create-email", email);
        log.info("Sended To Queue New Email Creation Report: {}", email);

        emailService.sendCreateEmailAlert(email);
    }

}
