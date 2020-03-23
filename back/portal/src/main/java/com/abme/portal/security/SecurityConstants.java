package com.abme.portal.security;

public final class SecurityConstants {
    static final String AUTHORIZATION_HEADER_NAME = "Authorization";
    static final String TOKEN_PREFIX = "Bearer ";
    static final long TOKEN_VALIDITY_IN_SECONDS = 864_000;
    static final String AUTHORITIES_KEY = "auth";

    private SecurityConstants() {
    }
}
