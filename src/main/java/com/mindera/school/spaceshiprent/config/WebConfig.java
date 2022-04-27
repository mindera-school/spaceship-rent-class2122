package com.mindera.school.spaceshiprent.config;

import com.mailjet.client.ClientOptions;
import com.mailjet.client.MailjetClient;
import com.mindera.school.spaceshiprent.properties.EmailSystemProperties;
import com.mindera.school.spaceshiprent.properties.SecurityProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@RequiredArgsConstructor
public class WebConfig {

    private final SecurityProperties securityProperties;
    private final EmailSystemProperties emailSystemProperties;

    @Bean
    public CorsFilter corsFilter() {
        final CorsConfiguration config = new CorsConfiguration();
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        config.addAllowedOrigin("*");

        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(
                securityProperties.getPasswordSaltSize());
    }

    @Bean
    public MailjetClient mailjetClient() {
        return new MailjetClient(
                emailSystemProperties.getMailjet().getKey(),
                emailSystemProperties.getMailjet().getSecret(),
                new ClientOptions("v3.1"));
    }
}
