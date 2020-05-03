package com.abme.portal.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter @Setter
@ConfigurationProperties(prefix = "security")
public class SecurityProperties {
    private String authorizationHeaderName;
    private String authoritiesKey;
    private Token token;

    @Getter @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Token {
        private String prefix;
        private long validityInSeconds;
    }
}
