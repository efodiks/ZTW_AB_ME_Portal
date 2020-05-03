package com.abme.portal.domain.authentication;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class JwtToken {
    private final String tokenString;
}
