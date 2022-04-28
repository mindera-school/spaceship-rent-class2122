package com.mindera.school.spaceshiprent.service.auth;


import com.mindera.school.spaceshiprent.converter.UserConverter;
import com.mindera.school.spaceshiprent.dto.user.LoginDto.LoginDto;
import com.mindera.school.spaceshiprent.dto.user.LoginDto.ValidLoginDto;
import com.mindera.school.spaceshiprent.persistence.entity.UserEntity;
import com.mindera.school.spaceshiprent.persistence.repository.UserRepository;
import com.mindera.school.spaceshiprent.util.JWTUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.security.auth.login.AccountNotFoundException;

import static com.mindera.school.spaceshiprent.exception.ErrorMessageConstants.WRONG_CREDENTIALS;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final UserConverter userConverter;
    private final JWTUtils jwtUtils;
    private final BCryptPasswordEncoder passwordEncoder;


    @Override
    public ValidLoginDto validLoginDto(LoginDto loginDto) throws AccountNotFoundException {

        UserEntity user = userRepository
                .findByEmail(loginDto.getEmail())
                .orElseThrow(() -> {
                    log.error("Given email could not be found - {}", loginDto.getEmail());
                    return new AccountNotFoundException((WRONG_CREDENTIALS));
                });

        if (!passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
            log.error("Given password doesn't match saved password");
            throw new AccountNotFoundException(WRONG_CREDENTIALS);
        }

        ValidLoginDto validLoginDto = userConverter.convertToValidLoginDto(user);
        validLoginDto.setToken(jwtUtils.generateToken(user));
        log.error("Login Successful");
        return validLoginDto;
    }
}
