package com.mindera.school.spaceshiprent.service.emailService;

import com.mindera.school.spaceshiprent.dto.user.UserDetailsDto;

public interface EmailService {

    void sendCreateEmailAlert(UserDetailsDto userDetailsDto);
}
