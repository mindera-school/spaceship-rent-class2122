package com.mindera.school.spaceshiprent.util;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.mindera.school.spaceshiprent.persistence.entity.UserEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTUtils {

    @Value("${jwt_secret}")
    private String secret;


    public String generateToken(UserEntity user) throws IllegalArgumentException, JWTCreationException {
        return JWT.create()
                .withSubject("User Info")
                .withClaim("id", user.getId())
                .withClaim("name", user.getName())
                .withClaim("email", user.getEmail())
                .withClaim("age", user.getAge())
                .withClaim("UserType", user.getUserType().name())
                .withIssuedAt(new Date())
                .withIssuer("SpaceShip")
                .sign(Algorithm.HMAC256(secret));
    }

    public String validateToken(String token) throws JWTVerificationException {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret))
                .withSubject("User Info")
                .withIssuer("SpaceShip")
                .build();
        DecodedJWT jwt = verifier.verify(token);
        return jwt.getClaim("email").asString();
    }
}
