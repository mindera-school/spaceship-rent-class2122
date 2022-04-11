package com.mindera.school.spaceshiprent.service.login;

import com.mindera.school.spaceshiprent.dto.login.UserLoginDto;
import com.mindera.school.spaceshiprent.dto.user.UserDetailsDto;

public interface LoginService {

    UserDetailsDto checkCredentials(UserLoginDto userLoginDto);
}
