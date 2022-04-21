package com.mindera.school.spaceshiprent.service.auth;

import com.mindera.school.spaceshiprent.dto.user.CredentialsDto;
import com.mindera.school.spaceshiprent.dto.user.LoginDto;

public interface AuthService {

    LoginDto login(CredentialsDto credentials);
}
