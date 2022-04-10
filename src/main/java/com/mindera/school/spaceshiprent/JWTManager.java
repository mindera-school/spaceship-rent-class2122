package com.mindera.school.spaceshiprent;

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

@RequiredArgsConstructor
@Component
public class JWTManager {

    @Value(value = "${jwt_secret}")
    private String SECRET;

    public String createToken(UserEntity entity) {
        return JWT.create()
                .withSubject("User Information")
                .withClaim("name", entity.getName())
                .withClaim("email", entity.getEmail())
                .withClaim("id", entity.getId())
                .withClaim("userType", entity.getUserType().getDeclaringClass().getName())
                .withClaim("Ssn", entity.getSsn())
                .withIssuedAt(Date.from(Instant.now()))
                .withIssuer("Spaceship-Rent-Mindera-School")
                .withExpiresAt(Date.from(Instant.now().plus(5L, ChronoUnit.MINUTES)))
                .sign(Algorithm.HMAC256(SECRET));
    }

    public Long validateTokenAndGetId(String token) {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET))
                .withSubject("User Information")
                .withIssuer("Spaceship-Rent-Mindera-School")
                .build();

        DecodedJWT jwt = verifier.verify(token);
        return jwt.getClaim("id").asLong();
    }

}
