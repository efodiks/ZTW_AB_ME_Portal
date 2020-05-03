package com.abme.portal.infrastracture.rest;

import com.abme.portal.domain.user.UserDto;
import com.abme.portal.domain.user.UserRegisterDto;
import com.abme.portal.domain.authentication.LoginDto;
import com.abme.portal.infrastracture.security.CustomUserDetailsService;
import com.abme.portal.domain.authentication.JwtToken;
import com.abme.portal.infrastracture.security.TokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class AuthenticationController {

    private final TokenProvider tokenProvider;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    private final CustomUserDetailsService customUserDetailsService;

    @PostMapping("/authenticate")
    public ResponseEntity<JwtToken> authorize(@Valid @RequestBody LoginDto loginDTO) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword());

        var authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        var jwtToken = tokenProvider.createToken(authentication, new Date());

        log.info(String.format("Authenticated user with email %s", loginDTO.getEmail()));
        return ResponseEntity.ok(jwtToken);
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> registerUser(@Valid @RequestBody UserRegisterDto userRegisterDto) throws URISyntaxException {
        var userDto = customUserDetailsService.registerUser(userRegisterDto);
        return ResponseEntity.created(new URI("/api/users/" + userDto.getUuid())).body(userDto);
    }
}
