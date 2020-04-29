package com.abme.portal.security;

import com.abme.portal.domain.User;
import com.abme.portal.dto.UserDTO;
import com.abme.portal.exceptions.AuthorityNotFoundException;
import com.abme.portal.repository.AuthorityRepository;
import com.abme.portal.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.stream.Collectors;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final UserRepository userRepository;

    private final AuthorityRepository authorityRepository;

    private final PasswordEncoder passwordEncoder;

    public UserDetailsService(UserRepository userRepository, AuthorityRepository authorityRepository, PasswordEncoder passwordEncoder) {
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
        var authority = authorityRepository.findById(AuthoritiesConstants.USER).orElseThrow(AuthorityNotFoundException::new);

        user.setAuthorities(Collections.singleton(authority));

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
        var grantedAuthorities = user
                .getAuthorities()
                .stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getName()))
                .collect(Collectors.toList());
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPasswordHash(),
                grantedAuthorities
        );
    }
}
