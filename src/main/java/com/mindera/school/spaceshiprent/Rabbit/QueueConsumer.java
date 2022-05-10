package com.mindera.school.spaceshiprent.Rabbit;

import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.errors.MailjetSocketTimeoutException;
import com.mindera.school.spaceshiprent.Gateway.MailJet;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class QueueConsumer {

private final MailJet mailJet;

    @RabbitListener(queues = "spaceShipQueue")
    public void receve(@Payload String email) throws MailjetSocketTimeoutException, MailjetException {
      log.info(email);
      mailJet.sendEmail();
    }
}
