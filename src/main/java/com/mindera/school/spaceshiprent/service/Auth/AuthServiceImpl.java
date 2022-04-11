package com.mindera.school.spaceshiprent.service.Auth;

import com.mindera.school.spaceshiprent.converter.UserConverter;
import com.mindera.school.spaceshiprent.dto.auth.LoginDetailsDto;
import com.mindera.school.spaceshiprent.dto.auth.LoginDto;
import com.mindera.school.spaceshiprent.exception.WrongCredentialsException;
import com.mindera.school.spaceshiprent.persistence.entity.UserEntity;
import com.mindera.school.spaceshiprent.persistence.repository.UserRepository;
import com.mindera.school.spaceshiprent.security.JWTUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import static com.mindera.school.spaceshiprent.exception.ErrorMessageConstants.WRONG_CREDENTIALS;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final UserConverter userConverter;
    private final JWTUtil jwtUtil;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public LoginDetailsDto login (LoginDto dto) {
        UserEntity userEntity = userRepository.findByEmail(dto.getEmail()).orElseThrow(() -> {
            log.error("Invalid email!");
            return new WrongCredentialsException(WRONG_CREDENTIALS);
        });
        if(!bCryptPasswordEncoder.matches(dto.getPassword(), userEntity.getPassword())) {
            log.error("Invalid password!");
            throw  new WrongCredentialsException(WRONG_CREDENTIALS);
        }

        return userConverter.convertToLoginDetailsDto(userEntity);
    }
}
