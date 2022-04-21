package com.mindera.school.spaceshiprent.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.mindera.school.spaceshiprent.persistence.entity.UserEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTUtil {

    @Value("${JWT_SECRET}")
    private String secret;

    public String generateToken(UserEntity userEntity) throws JWTCreationException {
        return JWT.create()
                .withSubject("User Details")
                .withClaim("id", userEntity.getId())
                .withClaim("name", userEntity.getName())
                .withClaim("age", userEntity.getAge())
                .withClaim("licenseNumber", userEntity.getLicenseNumber())
                .withClaim("ssn", userEntity.getSsn())
                .withClaim("planet", userEntity.getPlanet())
                .withClaim("email", userEntity.getEmail())
                .withClaim("userType", userEntity.getUserType().name())
                .withIssuedAt(new Date())
                .withIssuer("Rent-a-Spaceship/API/MinderaSchool")
                .sign(Algorithm.HMAC256(secret));
    }
}
