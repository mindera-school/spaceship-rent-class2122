package com.mindera.school.spaceshiprent.security.filter;

import com.mindera.school.spaceshiprent.security.UserAuthenticationProvider;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final static Logger LOGGER = LogManager.getLogger(JwtAuthFilter.class);

    private static final String BEARER_PREFIX = "Bearer";

    private final UserAuthenticationProvider userAuthenticationProvider;

    @Override
    protected void doFilterInternal(@NonNull final HttpServletRequest httpServletRequest,
                                    @NonNull final HttpServletResponse httpServletResponse,
                                    @NonNull final FilterChain filterChain) throws ServletException, IOException {

        final String header = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);

        if (Objects.nonNull(header)) {
            final String[] authElements = header.split(" ");

            if (authElements.length == 2 && BEARER_PREFIX.equals(authElements[0])) {
                try {
                    final Authentication auth = userAuthenticationProvider.validateToken(authElements[1]);

                    SecurityContextHolder.getContext().setAuthentication(auth);

                    LOGGER.info("Successfully authenticated with token");
                } catch (RuntimeException e) {
                    SecurityContextHolder.clearContext();
                    LOGGER.error("Failed to parse token", e);
                    throw e;
                }
            }
        }

        // Allways call this in order to tell Spring Security to continue
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
