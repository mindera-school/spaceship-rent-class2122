package com.mindera.school.spaceshiprent.security.authentication;

import com.mindera.school.spaceshiprent.enumerator.UserRole;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;

public class ApiKeyAuthenticationToken extends AbstractAuthenticationToken {

    private Object principal;
    public ApiKeyAuthenticationToken(Object principal) {
        super(Collections.singletonList(new SimpleGrantedAuthority(UserRole.API.name())));
        this.principal = principal;
        super.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }
}
