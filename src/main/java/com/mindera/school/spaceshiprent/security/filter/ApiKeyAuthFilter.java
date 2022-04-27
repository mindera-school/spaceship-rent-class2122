package com.mindera.school.spaceshiprent.security.filter;

import com.mindera.school.spaceshiprent.security.UserAuthenticationProvider;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
public class ApiKeyAuthFilter extends OncePerRequestFilter {

    private static final Logger LOGGER = LogManager.getLogger(ApiKeyAuthFilter.class);

    public static final String HEADER_API_KEY = "X-API-KEY";

    private final UserAuthenticationProvider userAuthenticationProvider;

    @Override
    protected void doFilterInternal(@NonNull final HttpServletRequest httpServletRequest,
                                    @NonNull final HttpServletResponse httpServletResponse,
                                    @NonNull final FilterChain filterChain) throws ServletException, IOException {

        final String apiKey = httpServletRequest.getHeader(HEADER_API_KEY);

        if (Objects.nonNull(apiKey)) {
            try {
                final Authentication auth = userAuthenticationProvider.validateApiKey(apiKey);

                SecurityContextHolder.getContext().setAuthentication(auth);

                LOGGER.info("Successfully authenticated with api key");
            } catch (RuntimeException e) {
                SecurityContextHolder.clearContext();
                LOGGER.error("Invalid api key", e);
                throw e;
            }
        }

        // Allways call this in order to tell Spring Security to continue
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
