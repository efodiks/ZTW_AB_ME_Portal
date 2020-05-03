package com.abme.portal.security;

import com.abme.portal.domain.user.RoleName;
import com.abme.portal.domain.user.User;
import com.abme.portal.dto.UserDTO;
import com.abme.portal.repository.AuthorityRepository;
import com.abme.portal.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;

@Service
public class CustomUserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final UserRepository userRepository;

    private final AuthorityRepository authorityRepository;

    private final PasswordEncoder passwordEncoder;

    public CustomUserDetailsService(UserRepository userRepository, AuthorityRepository authorityRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public User registerUser(UserDTO userDTO) {
        var user = new User()
                .setEmail(userDTO.getEmail().toLowerCase())
                .setPasswordHash(passwordEncoder.encode(userDTO.getPassword()))
                .setUsername(userDTO.getUsername())
                .setFirstName(userDTO.getFirstName())
                .setLastName(userDTO.getLastName())
                .setURL(userDTO.getURL());
        var authority = authorityRepository.findByName(RoleName.ROLE_USER);

        user.setRole(authority);

        userRepository.save(user);
        return user;
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
