package com.mindera.school.spaceshiprent.service.auth;

import com.mindera.school.spaceshiprent.dto.auth.LoginDetailsDto;
import com.mindera.school.spaceshiprent.dto.auth.LoginDto;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {
    LoginDetailsDto login (LoginDto loginDto);
}
