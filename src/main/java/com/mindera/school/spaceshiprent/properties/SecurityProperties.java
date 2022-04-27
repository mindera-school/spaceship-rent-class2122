package com.mindera.school.spaceshiprent.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "app.security")
public class SecurityProperties {

    private int passwordSaltSize;
    private String jwtSecret;
    private Long sessionDuration;
    private String sessionCookieName;
    private String apiKey;
}
