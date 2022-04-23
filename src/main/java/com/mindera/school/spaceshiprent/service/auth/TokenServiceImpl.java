package com.mindera.school.spaceshiprent.service.auth;

import com.mindera.school.spaceshiprent.dto.auth.PrincipalDto;
import com.mindera.school.spaceshiprent.enumerator.UserType;
import com.mindera.school.spaceshiprent.properties.SecurityProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

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
                "userType", principal.getUserType());

        // Calculate dates
        final Date issuedAt = new Date();
        final Date expiresAt = new Date(issuedAt.getTime() + securityProperties.getJwt().getExpiresIn());

        //Build token and compact to string
        return Jwts.builder()
                .setSubject(principal.getId().toString())
                .setClaims(claimsMap)
                .setIssuedAt(issuedAt)
                .setExpiration(expiresAt)
                .setIssuer("Spaceship-Rent-Mindera-School")
                .signWith(SignatureAlgorithm.HS256, securityProperties.getJwt().getSecret())
                .compact();
    }

    @Override
    public PrincipalDto validateTokenAndGetId(final String token) {

        final Jws<Claims> claims = Jwts.parser()
                .setSigningKey(securityProperties.getJwt().getSecret())
                .parseClaimsJws(token);

        return PrincipalDto.builder()
                .id(Long.getLong(claims.getBody().getSubject()))
                .email(claims.getBody().get("email", String.class))
                .name(claims.getBody().get("name", String.class))
                .userType(claims.getBody().get("userType", UserType.class))
                .build();
    }
}
