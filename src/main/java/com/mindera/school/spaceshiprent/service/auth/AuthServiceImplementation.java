package com.mindera.school.spaceshiprent.service.auth;

import com.mindera.school.spaceshiprent.converter.UserConverter;
import com.mindera.school.spaceshiprent.dto.auth.LoginDetailsDto;
import com.mindera.school.spaceshiprent.dto.auth.LoginDto;
import com.mindera.school.spaceshiprent.exception.AccountNotFoundException;
import com.mindera.school.spaceshiprent.exception.SpaceshipRentException;
import com.mindera.school.spaceshiprent.persistence.entity.UserEntity;
import com.mindera.school.spaceshiprent.persistence.repository.UserRepository;
import com.mindera.school.spaceshiprent.security.JWTutil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import static com.mindera.school.spaceshiprent.exception.ErrorMessageConstants.ACCOUNT_NOT_FOUND;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImplementation implements AuthService {

    private final UserRepository userRepository;
    private final UserConverter  userConverter;
    private final JWTutil jwtutil;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public LoginDetailsDto login(LoginDto loginDto) throws SpaceshipRentException {
        UserEntity user = userRepository.findByEmail(loginDto.getEmail()).orElseThrow(() -> {
            log.error("There is no account registered with this email.");
            return new AccountNotFoundException(ACCOUNT_NOT_FOUND);
        });
        if (!bCryptPasswordEncoder.matches(loginDto.getPassword(), user.getPassword())){
            log.error("This password does not match this email.");
            throw new AccountNotFoundException(ACCOUNT_NOT_FOUND);
        };


        return userConverter.convertToLoginDetailsDto(user);
    }
}
