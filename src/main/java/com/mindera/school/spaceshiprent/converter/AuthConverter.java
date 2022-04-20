package com.mindera.school.spaceshiprent.converter;


import com.mindera.school.spaceshiprent.dto.auth.SendTokenDTO;
import com.mindera.school.spaceshiprent.persistence.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component  
public class AuthConverter {

    public SendTokenDTO convertToSendTokenDTO(UserEntity user, String token) {
        return SendTokenDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .age(user.getAge())
                .email(user.getEmail())
                .userType(user.getUserType())
                .token(token)
                .build();
    }
}

