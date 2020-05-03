package com.abme.portal.rest;

import com.abme.portal.domain.user.User;
import com.abme.portal.dto.LoginDTO;
import com.abme.portal.dto.UserDTO;
import com.abme.portal.repository.UserRepository;
import com.abme.portal.exceptions.BadRequest;
import com.abme.portal.exceptions.EmailAlreadyUsedException;
import com.abme.portal.security.JwtToken;
import com.abme.portal.security.TokenProvider;
import com.abme.portal.security.CustomUserDetailsService;
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

@RestController
@RequestMapping("/api")
public class AuthenticationController {

    private final TokenProvider tokenProvider;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    private final UserRepository userRepository;

    private final CustomUserDetailsService customUserDetailsService;

    public AuthenticationController(TokenProvider tokenProvider, AuthenticationManagerBuilder authenticationManagerBuilder, UserRepository userRepository, CustomUserDetailsService customUserDetailsService) {
        this.tokenProvider = tokenProvider;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.userRepository = userRepository;
        this.customUserDetailsService = customUserDetailsService;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<JwtToken> authorize(@Valid @RequestBody LoginDTO loginDTO) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword());
        var authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        var jwtToken = tokenProvider.createToken(authentication, new Date());

        return ResponseEntity.ok(jwtToken);
    }

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@Valid @RequestBody UserDTO userDTO) throws URISyntaxException {
        if (userDTO.getId() != null) throw new BadRequest("User cannot already have and ID");
        if (userRepository.findOneByEmailIgnoreCase(userDTO.getEmail()).isPresent())
            throw new EmailAlreadyUsedException();
        var user = customUserDetailsService.registerUser(userDTO);
        return ResponseEntity.created(new URI("/api/users/" + user.getId())).body(user);
    }
}
