package com.mindera.school.spaceshiprent.security.filter;

import com.mindera.school.spaceshiprent.security.UserAuthenticationProvider;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

@RequiredArgsConstructor
public class CookieAuthFilter extends OncePerRequestFilter {

    private static final Logger LOGGER = LogManager.getLogger(CookieAuthFilter.class);

    public static final String COOKIE_NAME = "session";

    private final UserAuthenticationProvider userAuthenticationProvider;

    @Override
    protected void doFilterInternal(@NonNull final HttpServletRequest httpServletRequest,
                                    @NonNull final HttpServletResponse httpServletResponse,
                                    @NonNull final FilterChain filterChain) throws ServletException, IOException {

        final Optional<Cookie> authcookie = Stream.of(Optional.ofNullable(httpServletRequest.getCookies())
                .orElse(new Cookie[0]))
                .filter(cookie -> COOKIE_NAME.equals(cookie.getName()) &&
                                Objects.nonNull(cookie.getValue()) &&
                                !cookie.getValue().isEmpty())
                .findFirst();

        try {
            authcookie.ifPresent(cookie -> {
                SecurityContextHolder.getContext().setAuthentication(
                        userAuthenticationProvider.validateToken(cookie.getValue())
                );
                LOGGER.info("Successfully authenticated with cookie");
            });
        } catch (RuntimeException e) {
            SecurityContextHolder.clearContext();
            LOGGER.error("Failed to validate cookie", e);
        }

        // Allways call this in order to tell Spring Security to continue
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
