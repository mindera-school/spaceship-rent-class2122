package com.mindera.school.spaceshiprent.service.auth;

import com.mindera.school.spaceshiprent.converter.UserConverter;
import com.mindera.school.spaceshiprent.dto.user.CredentialsDto;
import com.mindera.school.spaceshiprent.dto.user.LoginDto;
import com.mindera.school.spaceshiprent.exception.ErrorMessageConstants;
import com.mindera.school.spaceshiprent.exception.WrongCredentialsException;
import com.mindera.school.spaceshiprent.persistence.entity.UserEntity;
import com.mindera.school.spaceshiprent.persistence.repository.UserRepository;
import com.mindera.school.spaceshiprent.util.JWTManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserConverter converter;
    private final JWTManager jwtManager;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Override
    public LoginDto login(CredentialsDto credentials) {

        UserEntity userEntity =
                userRepository.findByEmail(credentials.getEmail()).orElseThrow(() -> {
                    log.info("Email entered doesn't exist: {}", credentials.getEmail());
                    return new WrongCredentialsException(ErrorMessageConstants.WRONG_CREDENTIALS);
                });

        if (!passwordEncoder.matches(credentials.getPassword(), userEntity.getPassword())) {
            log.info("User inserted wrong credentials");
            throw new WrongCredentialsException(ErrorMessageConstants.WRONG_CREDENTIALS);
        }

        String token = jwtManager.createToken(userEntity);

        log.info("login token created");

        LoginDto convertedUser = converter.convertToLoginDto(userEntity);
        convertedUser.setToken(token);

        return convertedUser;
    }

}
