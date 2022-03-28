package com.mindera.school.spaceshiprent.components.gateway.email;

import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.errors.MailjetSocketTimeoutException;

public interface emailGateway {

    void sendEmail(String to, String subject, String content) throws MailjetSocketTimeoutException, MailjetException;

}
