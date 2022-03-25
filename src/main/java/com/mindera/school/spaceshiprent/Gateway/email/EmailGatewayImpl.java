package com.mindera.school.spaceshiprent.Gateway.email;

import com.mailjet.client.MailjetClient;
import com.mailjet.client.MailjetRequest;
import com.mailjet.client.MailjetResponse;
import com.mailjet.client.resource.Emailv31;
import com.mindera.school.spaceshiprent.config.CredentialsConfiguration;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class EmailGatewayImpl implements EmailGateway {

    private static final String MAILJET_API_KEY = CredentialsConfiguration.getCredentials("MAILJET_API_KEY");
    private static final String MAILJET_API_SECRET = CredentialsConfiguration.getCredentials("MAILJET_API_SECRET");

    private static final String MAILJET_SENDER_EMAIL = "rafael.martins@school.mindera.com";
    private static final String MAILJET_SENDER_NAME = "Spaceship Rent";


    @SneakyThrows
    @Override
    public void sendEmail(String to, String subject, String content) {
        log.info("Sending email to {} about {}", to, subject);
        final MailjetClient client = new MailjetClient(MAILJET_API_KEY, MAILJET_API_SECRET);
        final MailjetRequest request;
        request = new MailjetRequest(Emailv31.resource)
                .property(Emailv31.MESSAGES, new JSONArray()
                        .put(new JSONObject()
                                .put(Emailv31.Message.FROM, new JSONObject()
                                        .put("Email", MAILJET_SENDER_EMAIL)
                                        .put("Name", MAILJET_SENDER_NAME))
                                .put(Emailv31.Message.TO, new JSONArray()
                                        .put(new JSONObject()
                                                .put("Email", to)))
                                .put(Emailv31.Message.SUBJECT, subject)
                                .put(Emailv31.Message.HTMLPART, content)
                                .put(Emailv31.Message.CUSTOMID, "AppGettingStartedTest")));

        final MailjetResponse response = client.post(request);
        if (response.getStatus() != 200) {
            log.error("Error sending email: {}", response.getData());
        }

    }
}
