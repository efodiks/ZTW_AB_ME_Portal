package com.abme.portal.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.security.Key;
import java.util.Arrays;
import java.util.Date;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class TokenProvider {

    @Value("${security.secret}")
    public String secret;

    private Key key;

    private static long tokenValidityInMilliseconds = SecurityConstants.TOKEN_VALIDITY_IN_SECONDS * 1000;

    @PostConstruct
    public void init() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        key = Keys.hmacShaKeyFor(keyBytes);
    }

    public JwtToken createToken(Authentication authentication) {
        long now = new Date().getTime();
        var expiresAt = new Date(now + tokenValidityInMilliseconds);

        var authorities = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        String jwtString = Jwts.builder()
                .setSubject(authentication.getName())
                .claim(SecurityConstants.AUTHORITIES_KEY, authorities)
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(expiresAt)
                .compact();

        return new JwtToken(jwtString);
    }

    public Optional<Authentication> getAuthentication(JwtToken token) {
        try {
            return Optional.of(tryGetAuthentication(token));
        } catch (JwtException | IllegalArgumentException e) {
            return Optional.empty();
        }
    }

    private Authentication tryGetAuthentication(JwtToken token) {
        Jws<Claims> jws = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token.getTokenString());
        Claims claims = jws.getBody();
        var authorities = Arrays.stream(
                claims.get(SecurityConstants.AUTHORITIES_KEY)
                        .toString()
                        .split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        return new UsernamePasswordAuthenticationToken(claims.getSubject(), token, authorities);
    }
}
