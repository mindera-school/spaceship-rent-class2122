package com.mindera.school.spaceshiprent.security;

import com.mindera.school.spaceshiprent.security.filter.ApiKeyAuthFilter;
import com.mindera.school.spaceshiprent.security.filter.CookieAuthFilter;
import com.mindera.school.spaceshiprent.security.filter.JwtAuthFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserAuthenticationEntryPoint userAuthenticationEntryPoint;
    private final UserAuthenticationProvider userAuthenticationProvider;

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .exceptionHandling().authenticationEntryPoint(userAuthenticationEntryPoint)
                .and()
                .addFilterBefore(new JwtAuthFilter(userAuthenticationProvider), BasicAuthenticationFilter.class)
                .addFilterBefore(new ApiKeyAuthFilter(userAuthenticationProvider), JwtAuthFilter.class)
                .addFilterBefore(new CookieAuthFilter(userAuthenticationProvider), ApiKeyAuthFilter.class)
                .csrf().and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/auth/login")
                .permitAll()
                .antMatchers("/swagger-ui.html", "/swagger-ui/*", "/v3/api-docs", "/v3/api-docs/*")
                .permitAll()
                .anyRequest().authenticated();
    }
}
