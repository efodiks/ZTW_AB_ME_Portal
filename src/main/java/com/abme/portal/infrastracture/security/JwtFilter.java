package com.abme.portal.infrastracture.security;

import com.abme.portal.config.SecurityProperties;
import com.abme.portal.domain.authentication.JwtToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Slf4j
public class JwtFilter extends OncePerRequestFilter {
    private final TokenProvider tokenProvider;
    private final SecurityProperties securityProperties;

    public JwtFilter(TokenProvider tokenProvider, SecurityProperties securityProperties) {
        this.tokenProvider = tokenProvider;
        this.securityProperties = securityProperties;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        var authentication = getAuthentication(request);
        log.info(String.format("Request authentication is %s", authentication.toString()));
        authentication.ifPresent(SecurityContextHolder.getContext()::setAuthentication);
        chain.doFilter(request, response);
    }

    private Optional<Authentication> getAuthentication(HttpServletRequest request) {
        var prefixedAuthorizationToken = request.getHeader(securityProperties.getAuthorizationHeaderName());
        var tokenString = resolveToken(prefixedAuthorizationToken);
        return tokenString
                .map(JwtToken::new)
                .flatMap(tokenProvider::getAuthentication);
    }

    private Optional<String> resolveToken(String prefixedAuthorizationToken) {
        if (StringUtils.hasText(prefixedAuthorizationToken) && prefixedAuthorizationToken.startsWith(securityProperties.getToken().getPrefix())) {
            var authorizationToken = prefixedAuthorizationToken.substring(securityProperties.getToken().getPrefix().length());
            return StringUtils.hasText(authorizationToken) ? Optional.of(authorizationToken) : Optional.empty();
        }
        return Optional.empty();
    }
}
