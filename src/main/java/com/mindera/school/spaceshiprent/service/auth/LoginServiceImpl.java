package com.mindera.school.spaceshiprent.service.auth;

import com.mindera.school.spaceshiprent.converter.UserConverter;
import com.mindera.school.spaceshiprent.dto.auth.UserLoginDto;
import com.mindera.school.spaceshiprent.dto.auth.ValidLoginDto;
import com.mindera.school.spaceshiprent.exception.UserNotFoundException;
import com.mindera.school.spaceshiprent.persistence.entity.UserEntity;
import com.mindera.school.spaceshiprent.persistence.repository.UserRepository;
import com.mindera.school.spaceshiprent.security.JwtManager;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final UserRepository userRepository;
    private final UserConverter userConverter;
    private final JwtManager jwtManager;

    @Override
    public ValidLoginDto checkCredentials(UserLoginDto userLoginDto) {
        UserEntity userEntity = userRepository.findByEmail(userLoginDto.getEmail()).orElseThrow(() -> new UserNotFoundException("Wrong credentials"));

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (passwordEncoder.matches(userLoginDto.getPassword(), userEntity.getPassword())) {
            String token = jwtManager.generateToken(userConverter.convertToUserDetailsDto(userEntity));

            return ValidLoginDto.builder()
                    .userDetails(userConverter.convertToUserDetailsDto(userEntity))
                    .token(token)
                    .build();
        }


        throw new UserNotFoundException("Wrong credentials");
    }
}