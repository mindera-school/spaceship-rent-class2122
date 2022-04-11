package com.mindera.school.spaceshiprent.service.auth;

import com.mindera.school.spaceshiprent.converter.AuthConverter;
import com.mindera.school.spaceshiprent.dto.auth.LoginDTO;
import com.mindera.school.spaceshiprent.dto.auth.SendTokenDTO;
import com.mindera.school.spaceshiprent.persistence.entity.UserEntity;
import com.mindera.school.spaceshiprent.persistence.repository.UserRepository;
import com.mindera.school.spaceshiprent.security.authJWT;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final AuthConverter authConverter;
    private final authJWT authJWT;
    private final BCryptPasswordEncoder pwEncoder;

    @Override
    public SendTokenDTO effectuateLogin(LoginDTO loginDTO) {
        UserEntity user = userRepository.findByEmail(loginDTO.getEmail());

        if (user == null || !pwEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException();
        }
        return authConverter.convertToSendTokenDTO(user,authJWT.generateJWT(loginDTO));
    }
}




