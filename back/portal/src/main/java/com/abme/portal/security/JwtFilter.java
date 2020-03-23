package com.abme.portal.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class JwtFilter extends BasicAuthenticationFilter {
    private final TokenProvider tokenProvider;

    public JwtFilter(AuthenticationManager authenticationManager, TokenProvider tokenProvider) {
        super(authenticationManager);
        this.tokenProvider = tokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        var authentication = getAuthentication(request);
        authentication.ifPresent(SecurityContextHolder.getContext()::setAuthentication);
        chain.doFilter(request, response);
    }

    private Optional<Authentication> getAuthentication(HttpServletRequest request) {
        var prefixedAuthorizationToken = request.getHeader(SecurityConstants.AUTHORIZATION_HEADER_NAME);
        var tokenString = resolveToken(prefixedAuthorizationToken);
        return tokenString
                .map(JwtToken::new)
                .flatMap(tokenProvider::getAuthentication);
    }

    private Optional<String> resolveToken(String prefixedAuthorizationToken) {
        if (StringUtils.hasText(prefixedAuthorizationToken) && prefixedAuthorizationToken.startsWith(SecurityConstants.TOKEN_PREFIX)) {
            var authorizationToken = prefixedAuthorizationToken.substring(SecurityConstants.TOKEN_PREFIX.length());
            return StringUtils.hasText(authorizationToken) ? Optional.of(authorizationToken) : Optional.empty();
        }
        return Optional.empty();
    }
}
