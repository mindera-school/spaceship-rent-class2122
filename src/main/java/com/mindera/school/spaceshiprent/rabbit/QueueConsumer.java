package com.mindera.school.spaceshiprent.rabbit;


import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.errors.MailjetSocketTimeoutException;
import com.mindera.school.spaceshiprent.components.EmailConsumer;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class QueueConsumer {
    private final EmailConsumer mailJet;

    @RabbitListener(queues = "spaceShipQueue")
    public void receiveMessage(@Payload String email) throws MailjetSocketTimeoutException, MailjetException {
        log.info(email);
        mailJet.receive(email);
    }
}
