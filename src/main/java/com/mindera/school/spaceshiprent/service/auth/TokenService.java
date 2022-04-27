package com.mindera.school.spaceshiprent.service.auth;

import com.mindera.school.spaceshiprent.dto.auth.PrincipalDto;

public interface TokenService {

    String createToken(PrincipalDto principal);

    PrincipalDto validateToken(String token);

    PrincipalDto validateApiKey(final String apiKey);
}
