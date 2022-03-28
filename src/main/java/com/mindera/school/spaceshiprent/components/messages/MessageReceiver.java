package com.mindera.school.spaceshiprent.components.messages;

import com.mindera.school.spaceshiprent.components.gateway.MailChimp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
@Slf4j
@Component
public class MessageReceiver {

    @RabbitListener(queues = "email")
        public void receive(@Payload String object){
        log.info(object);
        MailChimp.pingMailChimp();
    }
}
