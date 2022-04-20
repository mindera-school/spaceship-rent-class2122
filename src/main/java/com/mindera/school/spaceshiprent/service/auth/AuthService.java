package com.mindera.school.spaceshiprent.service.auth;
import com.mindera.school.spaceshiprent.dto.auth.LoginDTO;
import com.mindera.school.spaceshiprent.dto.auth.SendTokenDTO;

public interface AuthService {

    SendTokenDTO effectuateLogin(LoginDTO loginDTO);
}

