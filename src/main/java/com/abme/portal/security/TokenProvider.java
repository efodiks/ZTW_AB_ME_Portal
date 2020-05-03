package com.abme.portal.security;

import com.abme.portal.config.JwtProperties;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Date;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Component
public class TokenProvider {

    JwtParser jwtParser;

    Key key;

    JwtProperties jwtProperties;

    private final long tokenValidityInMilliseconds;

    public TokenProvider(JwtParser jwtParser, Key key, JwtProperties jwtProperties) {
        this.jwtParser = jwtParser;
        this.key = key;
        this.jwtProperties = jwtProperties;
        tokenValidityInMilliseconds = jwtProperties.getToken().getValidityInSeconds() * 1000;
    }

    public JwtToken createToken(Authentication authentication, Date now) {
        var nowLong = now.getTime();
        var expiresAt = new Date(nowLong + tokenValidityInMilliseconds);

        var authorities = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        String jwtString = Jwts.builder()
                .setSubject(authentication.getName())
                .claim(jwtProperties.getAuthoritiesKey(), authorities)
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(expiresAt)
                .compact();

        return new JwtToken(jwtString);
    }

    public Optional<Authentication> getAuthentication(JwtToken token) {
        try {
            return Optional.of(tryGetAuthentication(token));
        } catch (JwtException | IllegalArgumentException e) {
            log.error(String.format("Couldn't get authorization from token %s", token));
            return Optional.empty();
        }
    }

    private Authentication tryGetAuthentication(JwtToken token) {
        Jws<Claims> jws = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token.getTokenString());
        Claims claims = jws.getBody();
        var authorities = Arrays.stream(
                claims.get(jwtProperties.getAuthoritiesKey())
                        .toString()
                        .split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        return new UsernamePasswordAuthenticationToken(claims.getSubject(), token, authorities);
    }
}
