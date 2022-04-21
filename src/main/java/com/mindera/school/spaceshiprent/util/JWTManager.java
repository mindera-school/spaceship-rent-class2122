package com.mindera.school.spaceshiprent.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.mindera.school.spaceshiprent.persistence.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import static java.lang.Long.parseLong;

@RequiredArgsConstructor
@Component
public class JWTManager {

    @Value(value = "${jwt_secret}")
    private String secret;

    public String createToken(UserEntity entity) {
        return JWT.create()
                .withSubject("User Information")
                .withClaim("name", entity.getName())
                .withClaim("email", entity.getEmail())
                .withClaim("id", entity.getId())
                .withClaim("userType", entity.getUserType().name())
                .withClaim("Ssn", entity.getSsn())
                .withIssuedAt(Date.from(Instant.now()))
                .withIssuer("Spaceship-Rent-Mindera-School")
                .withExpiresAt(Date.from(Instant.now().plus(5L, ChronoUnit.MINUTES)))
                .sign(Algorithm.HMAC256(secret));
    }

    public Long validateTokenAndGetId(String token) {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret))
                .withIssuer("Spaceship-Rent-Mindera-School")
                .build();

        DecodedJWT jwt = verifier.verify(token);
        return parseLong(jwt.getSubject());
    }

}
