package com.mindera.school.spaceshiprent.service.Auth;

import com.mindera.school.spaceshiprent.dto.auth.LoginDetailsDto;
import com.mindera.school.spaceshiprent.dto.auth.LoginDto;

public interface AuthService {

    LoginDetailsDto login (LoginDto dto);
}
