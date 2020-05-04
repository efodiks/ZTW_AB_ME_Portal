package com.abme.portal.domain.authentication;

import com.abme.portal.domain.role.RoleName;
import com.abme.portal.domain.role.RoleRepository;
import com.abme.portal.domain.user.*;
import com.abme.portal.exceptions.UserNotFoundException;
import com.abme.portal.infrastracture.security.TokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthenticationFacade {
    private final TokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;


    @Transactional
    public UserStubDto registerUser(UserRegisterDto userRegister) {
        var authority = roleRepository.findByName(RoleName.ROLE_USER);

        var user = new User(
                null,
                userRegister.getUuid(),
                userRegister.getEmail(),
                passwordEncoder.encode(userRegister.getPassword()),
                userRegister.getFirstName(),
                userRegister.getLastName(),
                userRegister.getUsername(),
                userRegister.getProfilePhotoUrl(),
                authority,
                Set.of(),
                Set.of(),
                Set.of()
        );

        userRepository.save(user);
        return UserStubDto.fromUser(user);
    }

    public LoginResponseDto tryToLoginUser(LoginDto loginDto) {
        var authentication = tryToAuthenticate(loginDto);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        var jwtToken = tokenProvider.createToken(authentication, new Date());
        var user = userRepository.findOneByEmailIgnoreCase(authentication.getName()).orElseThrow(UserNotFoundException::new);

        return new LoginResponseDto(
                jwtToken,
                UserDto.fromUser(user)
        );
    }

    private Authentication tryToAuthenticate(LoginDto loginDto) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword());
        return authenticationManagerBuilder.getObject().authenticate(authenticationToken);
    }
}
