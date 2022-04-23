package com.mindera.school.spaceshiprent.service.auth;

import com.mindera.school.spaceshiprent.dto.auth.PrincipalDto;
import com.mindera.school.spaceshiprent.persistence.entity.UserEntity;

public interface TokenService {

    String createToken(PrincipalDto principal);

    PrincipalDto validateTokenAndGetId(String token);
}
