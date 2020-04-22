package com.abme.portal.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter @Setter
@ConfigurationProperties(prefix = "security")
public class JwtProperties {
    private String authorizationHeaderName;
    private String authoritiesKey;
    private Token token;

    @Getter @Setter
    public static class Token {
        private String prefix;
        private long validityInSeconds;

        public Token() {
        }

        public Token(String prefix, long validityInSeconds) {
            this.prefix = prefix;
            this.validityInSeconds = validityInSeconds;
        }
    }
}
