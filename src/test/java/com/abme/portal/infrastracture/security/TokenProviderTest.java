package com.abme.portal.infrastracture.security;

import com.abme.portal.config.SecurityProperties;
import com.abme.portal.domain.role.RoleName;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;

class TokenProviderTest {

    TokenProvider tokenProvider;

    @Mock
    Authentication authentication;

    @Mock
    JwtParser jwtParser;

    @Mock
    SecurityProperties securityProperties;

    @BeforeEach
    void setUp() {
        var secret = "046R/udN1odIEiFOwO9AHEjsDiYrzP/nASCWicQC8JE/I/12pHGtO/mNt8E+xRv4VQoh8bcGlIxXC5rs17t7iA==";
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        var key = Keys.hmacShaKeyFor(keyBytes);

        MockitoAnnotations.initMocks(this);

        initJwtProperties();

        tokenProvider = new TokenProvider(jwtParser, key, securityProperties);
    }

    private void initJwtProperties() {
        doReturn(new SecurityProperties.Token("Bearer ", 864_000)).when(securityProperties).getToken();
        doReturn("auth").when(securityProperties).getAuthoritiesKey();
    }


    @Disabled("Issue with different timezones result in token different than expected ")
    @Test
    void createToken() {

        //given
        var calendar = new GregorianCalendar(2020, Calendar.JANUARY, 1, 12, 0, 0);
        var date = Date.from(calendar.toZonedDateTime().toInstant());

        var authorities = List.of(new SimpleGrantedAuthority(RoleName.ROLE_USER.name()));
        doReturn(authorities).when(authentication).getAuthorities();

        var name = "John Doe";
        doReturn(name).when(authentication).getName();


        //when
        var token = tokenProvider.createToken(authentication, date);

        //then
        assertEquals("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJKb2huIERvZSIsImF1dGgiOiJST0xFX1VTRVIiLCJleHAiOjE1Nzg3NDA0MDB9.6tSBQm-ZYnwcAixOI39WElHNMQzETO7rzACo1sYBCgu5RtCsII6ffdqd0XFOIEZX1qXEtET2KG5zdFIoM8Ov_g",
                token.getTokenString());
    }
}