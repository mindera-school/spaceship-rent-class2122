package com.mindera.school.spaceshiprent.service.auth;

import com.mindera.school.spaceshiprent.dto.auth.CredentialsDto;
import com.mindera.school.spaceshiprent.dto.auth.LoginResponseDto;

public interface AuthService {

    LoginResponseDto login(CredentialsDto credentials);
}
