package com.mindera.school.spaceshiprent.messaging;

import com.mindera.school.spaceshiprent.converter.ObjectConverter;
import com.mindera.school.spaceshiprent.dto.user.UserDetailsDto;
import com.mindera.school.spaceshiprent.service.emailService.EmailServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Slf4j
@Component
@RequiredArgsConstructor
public class Consumer {

    private final EmailServiceImpl emailService;


    @RabbitListener(queues =  "create-email")
    private void receiveCreateEmailQueue(@Payload  String userDetails) {
        log.info("Received message from create-email queue");
        HashMap<String, String> userDetailsMap = ObjectConverter.convertStringToObject(userDetails);
        UserDetailsDto userDetailsDto = UserDetailsDto.builder()
                .name(userDetailsMap.get("name"))
                .email(userDetailsMap.get("email"))
                .age(Integer.parseInt(userDetailsMap.get("age")))
                .licenseNumber(userDetailsMap.get("licenseNumber"))
                .ssn(Long.parseLong(userDetailsMap.get("ssn")))
                .planet(userDetailsMap.get("planet"))
                .build();

        emailService.sendCreateEmailAlert(userDetailsDto);

    }
}
