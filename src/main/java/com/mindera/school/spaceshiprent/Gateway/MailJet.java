package com.mindera.school.spaceshiprent.Gateway;

import com.mailjet.client.ClientOptions;
import com.mailjet.client.MailjetClient;
import com.mailjet.client.MailjetRequest;
import com.mailjet.client.MailjetResponse;
import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.errors.MailjetSocketTimeoutException;
import com.mailjet.client.resource.Emailv31;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

@Component
public class MailJet {

        public void sendEmail() throws MailjetException, MailjetSocketTimeoutException {
            MailjetClient client;
            MailjetRequest request;
            MailjetResponse response;
            client = new MailjetClient("7e1cd326a2c4d344a8432f2eb8534814","14516914fb7e8ee06867849fc479830a", new ClientOptions("v3.1"));
            request = new MailjetRequest(Emailv31.resource)
                    .property(Emailv31.MESSAGES, new JSONArray()
                            .put(new JSONObject()
                                    .put(Emailv31.Message.FROM, new JSONObject()
                                            .put("Email", "joao.moutinho@school.mindera.com")
                                            .put("Name", "João"))
                                    .put(Emailv31.Message.TO, new JSONArray()
                                            .put(new JSONObject()
                                                    .put("Email", "jonasmoutinho5@hotmail.com")
                                                    .put("Name", "João")))
                                    .put(Emailv31.Message.SUBJECT, "Greetings from Mailjet.")
                                    .put(Emailv31.Message.TEXTPART, "My first Mailjet email")
                                    .put(Emailv31.Message.HTMLPART, "<h3>Dear passenger 1, welcome to <a href='https://www.mailjet.com/'>Mailjet</a>!</h3><br />May the delivery force be with you!")
                                    .put(Emailv31.Message.CUSTOMID, "AppGettingStartedTest")));
            response = client.post(request);
            System.out.println(response.getStatus());
            System.out.println(response.getData());
        }
}
