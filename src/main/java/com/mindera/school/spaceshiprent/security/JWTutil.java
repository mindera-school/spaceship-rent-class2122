package com.mindera.school.spaceshiprent.security;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.mindera.school.spaceshiprent.persistence.entity.UserEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTutil {

    @Value("${SECRET}")
    private String secret;

    public String createToken(UserEntity userEntity) throws JWTCreationException {
        return JWT.create()
                .withSubject("User Data")
                .withClaim("id", userEntity.getId())
                .withClaim("name", userEntity.getName())
                .withClaim("planet", userEntity.getPlanet())
                .withClaim("email", userEntity.getEmail())
                .withClaim("userType", userEntity.getUserType().name())
                .withIssuedAt(new Date())
                .withIssuer("Rent-a-spaceship/API/Mindera-School")
                .sign(Algorithm.HMAC256(secret));

    }

}
