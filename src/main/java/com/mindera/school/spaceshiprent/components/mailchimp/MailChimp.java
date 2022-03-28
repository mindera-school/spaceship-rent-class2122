package com.mindera.school.spaceshiprent.components.mailchimp;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

import org.springframework.web.client.RestTemplate;
import java.util.Base64;

public class MailChimp {

    public static void pingMailChimp() {
        String path = "https://us14.api.mailchimp.com/3.0/ping";

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();

        String auth = "anystring:ba4cc6330733ac4b8e1e58e9a41b446b-us14";
        String base64Cred = Base64.getEncoder().encodeToString(auth.getBytes());
        httpHeaders.add("Authorization", "Basic " + base64Cred);

        HttpEntity<Object> entity = new HttpEntity<>(httpHeaders);

        HttpEntity<String> response = restTemplate.exchange(
                path,
                HttpMethod.GET,
                entity,
                String.class
        );
        System.out.println(response);
    }
}