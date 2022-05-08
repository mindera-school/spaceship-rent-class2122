package com.mindera.school.spaceshiprent.components;

import com.mailjet.client.ClientOptions;
import com.mailjet.client.MailjetClient;
import com.mailjet.client.MailjetRequest;
import com.mailjet.client.MailjetResponse;
import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.errors.MailjetSocketTimeoutException;
import com.mailjet.client.resource.Emailv31;
import com.mindera.school.spaceshiprent.properties.ThirdPartyProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class EmailConsumer {

    private final ThirdPartyProperties properties;

    @RabbitListener(queues = "emailQueue")
    public void receive(@Payload String emailingInfo) throws MailjetSocketTimeoutException, MailjetException {
        String email = emailingInfo.split(" ")[0];
        String name = emailingInfo.split(" ")[1];

        MailjetClient client;
        MailjetRequest request;
        MailjetResponse response;


        client = new MailjetClient(properties.getMailjet().getKey(), properties.getMailjet().getSecretkey(), new ClientOptions("v3.1"));
        request = new MailjetRequest(Emailv31.resource)
                .property(Emailv31.MESSAGES, new JSONArray()
                        .put(new JSONObject()
                                .put(Emailv31.Message.FROM, new JSONObject()
                                        .put("Email", "filipa.silva@school.mindera.com")
                                        .put("Name", "Spaceship-Rent, S.A."))
                                .put(Emailv31.Message.TO, new JSONArray()
                                        .put(new JSONObject()
                                                .put("Email", email)
                                                .put("Name", name)))
                                .put(Emailv31.Message.SUBJECT, "Welcome to spaceship-rent")
                                .put(Emailv31.Message.TEXTPART, "Spaceship rent")
                                .put(Emailv31.Message.HTMLPART, "<h3>Welcome " + name + ", to our spaceship rent service</h3><br/>" +
                                        "you can start renting spaceships <a href='http://localhost:8080/swagger-ui/index.html#/'>here</a>!<br/>" +
                                        "Hope you enjoy our spaceships and customer service :)")
                                .put(Emailv31.Message.CUSTOMID, "spaceshipRentMailingSystem")));
        response = client.post(request);

        log.info("Response status: {}", response.getStatus());
        log.info("Response information: {}", response.getData());
        log.info("Sent an email to client");
    }

}
