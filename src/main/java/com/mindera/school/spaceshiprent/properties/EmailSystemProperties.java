package com.mindera.school.spaceshiprent.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Data
@Component
@ConfigurationProperties(prefix = "app.mail.third-party")
public class EmailSystemProperties {

    Keys mailjet;

    @Data
    public static class Keys {
        private String key;
        private String secret;
    }

}
