package com.mindera.school.spaceshiprent.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.mindera.school.spaceshiprent.dto.auth.LoginDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class authJWT {
    @Value("${secret}")
    private String secret;

    public String generateJWT(LoginDTO loginDTO) {
        return JWT.create().withSubject("Login-User")
                .withClaim("email", loginDTO.getEmail())
                .withClaim("password", loginDTO.getPassword())
                .withIssuedAt(new java.util.Date())
                .withIssuer("SpaceshipRent")
                .withExpiresAt(new java.util.Date(System.currentTimeMillis() + 86400000)) // 1 day
                .sign(Algorithm.HMAC256(secret));
    }

    public String decodeJWT(LoginDTO loginDTO, String token) {
        JWT.decode(token).getSubject();
        return null;
    }
}


