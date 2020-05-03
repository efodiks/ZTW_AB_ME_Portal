package com.abme.portal.infrastracture.security;

import com.abme.portal.domain.role.RoleName;
import com.abme.portal.domain.user.User;
import com.abme.portal.domain.user.UserDto;
import com.abme.portal.domain.role.RoleRepository;
import com.abme.portal.domain.user.UserRegisterDto;
import com.abme.portal.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserDto registerUser(UserRegisterDto userRegister) {
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
                Set.of()
        );

        userRepository.save(user);
        return UserDto.from(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findOneByEmailIgnoreCase(username)
                .map(this::createSpringSecurityUser)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("Username with email %s not found", username)));
    }

    private org.springframework.security.core.userdetails.User createSpringSecurityUser(User user) {
        var userAuthority = new SimpleGrantedAuthority(user.getRole().getName().name());
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPasswordHash(),
                Collections.singleton(userAuthority)
        );
    }
}
