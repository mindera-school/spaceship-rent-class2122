package com.mindera.school.spaceshiprent.service.auth;

import com.mindera.school.spaceshiprent.dto.auth.UserLoginDto;
import com.mindera.school.spaceshiprent.dto.auth.ValidLoginDto;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {


    ValidLoginDto login (UserLoginDto dto);

}
