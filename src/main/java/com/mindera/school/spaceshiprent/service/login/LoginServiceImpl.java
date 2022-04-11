package com.mindera.school.spaceshiprent.service.login;

import com.mindera.school.spaceshiprent.converter.UserConverter;
import com.mindera.school.spaceshiprent.dto.login.UserLoginDto;
import com.mindera.school.spaceshiprent.dto.user.UserDetailsDto;
import com.mindera.school.spaceshiprent.persistence.entity.UserEntity;
import com.mindera.school.spaceshiprent.persistence.repository.UserRepository;
import com.mindera.school.spaceshiprent.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final UserRepository userRepository;
    private final UserConverter userConverter;

    @Override
    public UserDetailsDto checkCredentials(UserLoginDto userLoginDto) {

        UserEntity userEntity = userRepository.findByEmail(userLoginDto.getEmail()).orElseThrow(() -> new RuntimeException("Wrong credentials"));

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if(passwordEncoder.matches(userLoginDto.getPassword(), userEntity.getPassword())) {
            return userConverter.convertToUserDetailsDto(userEntity);
        }


        throw new RuntimeException("Wrong credentials");
    }
}