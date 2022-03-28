package com.mindera.school.spaceshiprent.components.gateway.email;


import com.mailjet.client.MailjetClient;
import com.mailjet.client.MailjetRequest;
import com.mailjet.client.MailjetResponse;
import com.mailjet.client.ClientOptions;
import com.mailjet.client.resource.Emailv31;
import lombok.SneakyThrows;
import org.json.JSONArray;
import org.json.JSONObject;

public class EmailGatewayImpl implements emailGateway {
        MailjetClient client;
        MailjetRequest request;
        MailjetResponse response;

        @SneakyThrows
        @Override
        public void sendEmail(String to, String subject, String content){
        client = new MailjetClient("b21a8467ed7882e93db57f97961c80bb","27dafe402d146feab3dd41dc90f82c4f", new ClientOptions("v3.1"));
        request = new MailjetRequest(Emailv31.resource)
                .property(Emailv31.MESSAGES, new JSONArray()
                        .put(new JSONObject()
                                .put(Emailv31.Message.FROM, new JSONObject()
                                        .put("Email", "rui.teixeira@school.mindera.com")
                                        .put("Name", "Rui"))
                                .put(Emailv31.Message.TO, new JSONArray()
                                        .put(new JSONObject()
                                                .put("Email", to)))
                                .put(Emailv31.Message.SUBJECT, subject)
                                .put(Emailv31.Message.HTMLPART, content)
                                .put(Emailv31.Message.CUSTOMID, "AppGettingStartedTest")));
        response = client.post(request);
        System.out.println(response.getStatus());
        System.out.println(response.getData());
    }
}