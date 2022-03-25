package com.mindera.school.spaceshiprent.service.emailService;


import com.mindera.school.spaceshiprent.Gateway.email.EmailGatewayImpl;
import com.mindera.school.spaceshiprent.config.EmailTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService {


    private final EmailGatewayImpl emailSender;
    private final EmailTemplate emailTemplateService;

    @Override
    public void sendCreateEmailAlert(String email) {
        System.out.println("Email sent to " + email + " with subject: Create Account");

        emailSender.sendEmail(email, EmailTemplate.getSubjectTemplate("create-email"), EmailTemplate.getBodyTemplate("create-email"));
    }
}
