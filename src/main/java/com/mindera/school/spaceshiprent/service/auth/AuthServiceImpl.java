package com.mindera.school.spaceshiprent.service.auth;

import com.mindera.school.spaceshiprent.converter.UserConverter;
import com.mindera.school.spaceshiprent.dto.auth.UserLoginDto;
import com.mindera.school.spaceshiprent.dto.auth.ValidLoginDto;
import com.mindera.school.spaceshiprent.exception.AccountNotFoundException;
import com.mindera.school.spaceshiprent.exception.SpaceshipRentException;
import com.mindera.school.spaceshiprent.persistence.entity.UserEntity;
import com.mindera.school.spaceshiprent.persistence.repository.UserRepository;
import com.mindera.school.spaceshiprent.security.JWTUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import static com.mindera.school.spaceshiprent.exception.ErrorMessageConstants.ACCOUNT_NOT_FOUND;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final UserConverter userConverter;
    private final JWTUtil jwtUtil;
    private final BCryptPasswordEncoder passwordEncoder;


    @Override
    public ValidLoginDto login(UserLoginDto dto) throws SpaceshipRentException {
        UserEntity user = userRepository.findByEmail(dto.getEmail()).orElseThrow(() -> {
            log.error("No account found with email: {}", dto.getEmail());
            return new AccountNotFoundException(ACCOUNT_NOT_FOUND);
        });
        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            log.error("Invalid password for email {}", dto.getEmail());
            throw new AccountNotFoundException(ACCOUNT_NOT_FOUND);
        }
        String token = jwtUtil.generateToken(user);

        return userConverter.convertToValidLoginDto(user, token);
    }
}


