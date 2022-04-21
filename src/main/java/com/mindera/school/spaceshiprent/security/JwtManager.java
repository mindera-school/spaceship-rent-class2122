package com.mindera.school.spaceshiprent.security;

import com.mindera.school.spaceshiprent.dto.user.UserDetailsDto;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import lombok.Data;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@ConfigurationProperties(prefix = "app.security.jwt")
@Data

public class JwtManager {
    private String secret;
    private long expiration;

    public String generateToken(UserDetailsDto userDetailsDto) {
        return Jwts.builder()
                .setSubject("User Details")
                .addClaims(userDetailsDto.toMap())
                .setExpiration(generateExpirationDate())
                .signWith(io.jsonwebtoken.SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public boolean validateToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJwt(token).getBody().getSubject().equals("User Details");
    }

    private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + expiration * 1000);
    }
}

