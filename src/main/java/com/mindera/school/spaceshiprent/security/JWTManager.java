package com.mindera.school.spaceshiprent.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.mindera.school.spaceshiprent.persistence.entity.UserEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTManager {

    @Value("${jwt_secret}")
    private String secret;

    public String generateToken(UserEntity userEntity) {
        return JWT.create()
                .withSubject("User Details")
                .withClaim("id", userEntity.getId())
                .withClaim("name", userEntity.getName())
                .withClaim("ssn", userEntity.getSsn())
                .withClaim("email", userEntity.getEmail())
                .withClaim("userType",userEntity.getUserType().name())
                .withIssuedAt(new Date())
                .withIssuer("Spaceship-rent-Mindera-School")
                .sign(Algorithm.HMAC256(secret));
    }

  /*  public String validateTokenAndRetrieveSubject(String token) {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret))
                .withSubject("User Details")
                .withIssuer("Spaceship-rent-Mindera-School")
                .build();
        DecodedJWT jwt = verifier.verify(token);
        return jwt.getClaim("email").asString();
    } */

}