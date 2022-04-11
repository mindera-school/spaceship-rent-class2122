package com.mindera.school.spaceshiprent.service.auth;

import com.mindera.school.spaceshiprent.dto.auth.LoginDto;
import com.mindera.school.spaceshiprent.dto.auth.SendTokenDto;

public interface AuthService {

    SendTokenDto loginToken(LoginDto loginDto);
}
