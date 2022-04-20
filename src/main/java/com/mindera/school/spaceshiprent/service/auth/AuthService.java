package com.mindera.school.spaceshiprent.service.auth;

import com.mindera.school.spaceshiprent.dto.user.LoginDto.LoginDto;
import com.mindera.school.spaceshiprent.dto.user.LoginDto.ValidLoginDto;

import javax.security.auth.login.AccountNotFoundException;

public interface AuthService {

    ValidLoginDto validLoginDto(LoginDto loginDto) throws AccountNotFoundException;
}
