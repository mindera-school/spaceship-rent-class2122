package com.mindera.school.spaceshiprent.security;

import com.mindera.school.spaceshiprent.dto.auth.PrincipalDto;
import com.mindera.school.spaceshiprent.security.authentication.ApiKeyAuthenticationToken;
import com.mindera.school.spaceshiprent.service.auth.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collections;

/**
 * Authentication provider
 */
@Component
@RequiredArgsConstructor
public class UserAuthenticationProvider {

    private final TokenService tokenService;

    public Authentication validateToken(String token) {
        // validate jwt token and get principal
        PrincipalDto principal = tokenService.validateToken(token);

        return new UsernamePasswordAuthenticationToken(
                principal,
                null,
                Collections.singletonList(new SimpleGrantedAuthority(principal.getUserRole().name()))
        );
    }

    public Authentication validateApiKey(String apiKey) {
        // validate api key and get principal
        PrincipalDto principal = tokenService.validateApiKey(apiKey);

        return new ApiKeyAuthenticationToken(principal);
    }
}
