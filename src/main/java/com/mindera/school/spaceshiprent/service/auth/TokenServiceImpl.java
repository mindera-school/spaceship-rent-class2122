package com.mindera.school.spaceshiprent.service.auth;

import com.mindera.school.spaceshiprent.dto.auth.PrincipalDto;
import com.mindera.school.spaceshiprent.enumerator.UserRole;
import com.mindera.school.spaceshiprent.enumerator.UserType;
import com.mindera.school.spaceshiprent.exception.exceptions.InvalidApiKeyException;
import com.mindera.school.spaceshiprent.properties.SecurityProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    private final SecurityProperties securityProperties;

    @Override
    public String createToken(final PrincipalDto principal) {
        // Set claims
        final Map<String, Object> claimsMap = Map.of(
                "name", principal.getName(),
                "email", principal.getEmail(),
                "userRole", principal.getUserRole());

        // Calculate dates
        final Date issuedAt = new Date();
        final Date expiresAt = new Date(issuedAt.getTime() + securityProperties.getSessionDuration());

        //Build token and compact to string
        return Jwts.builder()
                .setSubject(principal.getId().toString())
                .addClaims(claimsMap)
                .setIssuedAt(issuedAt)
                .setExpiration(expiresAt)
                .setIssuer("Spaceship-Rent-Mindera-School")
                .signWith(SignatureAlgorithm.HS256, securityProperties.getJwtSecret())
                .compact();
    }

    @Override
    public PrincipalDto validateToken(final String token) {

        final Jws<Claims> claims = Jwts.parser()
                .setSigningKey(securityProperties.getJwtSecret())
                .parseClaimsJws(token);

        return PrincipalDto.builder()
                .id(Long.getLong(claims.getBody().getSubject()))
                .email(claims.getBody().get("email", String.class))
                .name(claims.getBody().get("name", String.class))
                .userRole(claims.getBody().get("userRole", UserRole.class))
                .build();
    }

    @Override
    public PrincipalDto validateApiKey(final String apiKey) {

        if (!apiKey.equals(securityProperties.getApiKey())) {
            throw new InvalidApiKeyException("Invalid API key");
        }

        return PrincipalDto.builder()
                .userRole(UserRole.API)
                .build();
    }
}
