package com.mindera.school.spaceshiprent.service.auth;

import com.mindera.school.spaceshiprent.dto.auth.UserLoginDto;
import com.mindera.school.spaceshiprent.dto.auth.ValidLoginDto;

public interface LoginService {

    ValidLoginDto checkCredentials(UserLoginDto userLoginDto);
}
