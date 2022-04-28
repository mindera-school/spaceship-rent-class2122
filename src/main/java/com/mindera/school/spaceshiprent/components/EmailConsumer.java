package com.mindera.school.spaceshiprent.components;

import com.mailjet.client.ClientOptions;
import com.mailjet.client.MailjetClient;
import com.mailjet.client.MailjetRequest;
import com.mailjet.client.MailjetResponse;
import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.errors.MailjetSocketTimeoutException;
import com.mailjet.client.resource.Emailv31;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class EmailConsumer {

    private final Logger LOGGER = LoggerFactory.getLogger(EmailConsumer.class);

    @Value(value = "${key}")
    private String key;

    @Value(value = "${secret-key}")
    private String secretKey;

    //@RabbitListener(queues = "emailQueue")
    public void receive(@Payload String emailingInfo) throws MailjetSocketTimeoutException, MailjetException {

        String email = emailingInfo.split(" ")[0];
        String name = emailingInfo.split(" ")[1];
        MailjetClient client;
        MailjetRequest request;
        MailjetResponse response;
        client = new MailjetClient(key, secretKey, new ClientOptions("v3.1"));
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
        System.out.println(response.getStatus());
        System.out.println(response.getData());
        LOGGER.info("Sent an email to client");
    }

}
