package com.mindera.school.spaceshiprent.service.emailService;


import com.mindera.school.spaceshiprent.Gateway.email.EmailGatewayImpl;
import com.mindera.school.spaceshiprent.config.EmailTemplate;
import com.mindera.school.spaceshiprent.dto.user.UserDetailsDto;
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
    public void sendCreateEmailAlert(UserDetailsDto userDetailsDto) {
        log.info("Sending email to {}", userDetailsDto.getEmail());
        emailSender.sendEmail(userDetailsDto.getEmail(), EmailTemplate.getBodyTemplate("create-email",
                    userDetailsDto.getName(),
                    userDetailsDto.getEmail(),
                    String.valueOf(userDetailsDto.getAge()),
                    userDetailsDto.getLicenseNumber(),
                    String.valueOf(userDetailsDto.getSsn()),
                    userDetailsDto.getPlanet()),
                EmailTemplate.getBodyTemplate("create-email"));
    }
}
