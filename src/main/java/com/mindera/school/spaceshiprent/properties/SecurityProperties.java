package com.mindera.school.spaceshiprent.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "app.security")
public class SecurityProperties {

    private JwtProperties jwt;
    private PasswordEncoderProperties passwordEncoder;

    @Data
    public static class JwtProperties {
        private String secret;
        private Long expiresIn;
    }

    @Data
    public static class PasswordEncoderProperties {
        private Integer saltSize;
    }
}
