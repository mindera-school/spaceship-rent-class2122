package com.mindera.school.spaceshiprent.service.auth;

import com.mindera.school.spaceshiprent.Security.Jwt;
import com.mindera.school.spaceshiprent.converter.AuthConverter;
import com.mindera.school.spaceshiprent.dto.auth.LoginDto;
import com.mindera.school.spaceshiprent.dto.auth.SendTokenDto;
import com.mindera.school.spaceshiprent.persistence.entity.UserEntity;
import com.mindera.school.spaceshiprent.persistence.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {


    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AuthConverter authConverter;


    private final Jwt jwt;

    @Override
    public SendTokenDto loginToken(LoginDto loginDto) {
        UserEntity user = userRepository.findByEmail(loginDto.getEmail());

        if(user == null || !bCryptPasswordEncoder.matches(loginDto.getPassword(), user.getPassword())){
            throw new IllegalArgumentException();
        }

        return authConverter.convertToSendToken(user, jwt.generateJWT(loginDto));
    }
}
