package com.mindera.school.spaceshiprent.service.auth;

import com.mindera.school.spaceshiprent.converter.AuthConverter;
import com.mindera.school.spaceshiprent.dto.auth.CredentialsDto;
import com.mindera.school.spaceshiprent.dto.auth.LoginResponseDto;
import com.mindera.school.spaceshiprent.dto.auth.PrincipalDto;
import com.mindera.school.spaceshiprent.exception.exceptions.WrongCredentialsException;
import com.mindera.school.spaceshiprent.persistence.entity.UserEntity;
import com.mindera.school.spaceshiprent.persistence.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.mindera.school.spaceshiprent.exception.ErrorMessageConstants.WRONG_CREDENTIALS;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final TokenService tokenService;
    private final PasswordEncoder passwordEncoder;
    private final AuthConverter authConverter;

    @Override
    public LoginResponseDto login(final CredentialsDto credentials) {
        final UserEntity userEntity =
                userRepository.findByEmail(credentials.getEmail()).orElseThrow(() -> {
                    log.error("Email entered doesn't exist: {}", credentials.getEmail());
                    return new WrongCredentialsException(WRONG_CREDENTIALS);
                });

        if (!passwordEncoder.matches(credentials.getPassword(), userEntity.getPassword())) {
            log.error("User inserted wrong credentials");
            throw new WrongCredentialsException("Email or password are wrong");
        }

        final PrincipalDto principalDto = authConverter.convertToPrincipalDto(userEntity);
        final String token = tokenService.createToken(principalDto);

        log.info("User {} successfully authenticated", principalDto.getId());

        return LoginResponseDto.builder()
                .principal(principalDto)
                .token(token)
                .build();
    }
}
