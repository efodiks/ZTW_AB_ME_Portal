package com.abme.portal.infrastracture.rest;

import com.abme.portal.domain.authentication.AuthenticationFacade;
import com.abme.portal.domain.authentication.LoginResponseDto;
import com.abme.portal.domain.user.UserStubDto;
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

    private final AuthenticationFacade authenticationFacade;

    @PostMapping("/authenticate")
    public ResponseEntity<LoginResponseDto> authorize(@Valid @RequestBody LoginDto loginDTO) {
        var loginResponseDto = authenticationFacade.tryToLoginUser(loginDTO);
        log.info(String.format("Authenticated user with email %s", loginDTO.getEmail()));
        return ResponseEntity.ok(loginResponseDto);
    }

    @PostMapping("/register")
    public ResponseEntity<UserStubDto> registerUser(@Valid @RequestBody UserRegisterDto userRegisterDto) throws URISyntaxException {
        var userStubDto = authenticationFacade.registerUser(userRegisterDto);
        log.info(String.format("Registering user with email %s", userRegisterDto.getEmail()));
        return ResponseEntity.created(new URI("/api/users/" + userStubDto.getUuid())).body(userStubDto);
    }
}
