package com.mindera.school.spaceshiprent.Gateway.email;

public interface EmailGateway {

    void sendEmail(String to, String subject, String content);
}
