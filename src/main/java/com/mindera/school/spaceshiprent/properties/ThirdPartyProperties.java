package com.mindera.school.spaceshiprent.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "app.third-party")
public class ThirdPartyProperties {

    private Keys mailjet;

    public static class Keys {
        private String key;
        private String secretkey;

        public String getKey() {
            return key;
        }

        public String getSecretkey() {
            return secretkey;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public void setSecretkey(String secretkey) {
            this.secretkey = secretkey;
        }
    }
}
