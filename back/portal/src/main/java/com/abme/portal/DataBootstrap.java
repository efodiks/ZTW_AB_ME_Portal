package com.abme.portal;


import com.abme.portal.domain.Authority;
import com.abme.portal.dto.PostDTO;
import com.abme.portal.dto.UserDTO;
import com.abme.portal.repository.AuthorityRepository;
import com.abme.portal.rest.PostController;
import com.abme.portal.security.AuthoritiesConstants;
import com.abme.portal.security.UserDetailsService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

@Component
public class DataBootstrap implements CommandLineRunner {

    private final AuthorityRepository authorityRepository;

    private final UserDetailsService userDetailsService;

    public DataBootstrap(AuthorityRepository authorityRepository, UserDetailsService userDetailsService) {
        this.authorityRepository = authorityRepository;

        this.userDetailsService = userDetailsService;
    }

    @Override
    public void run(String... args) throws Exception {
        Stream.of(AuthoritiesConstants.ANONYMOUS, AuthoritiesConstants.USER, AuthoritiesConstants.ADMIN)
                .map(Authority::new)
                .forEach(authorityRepository::save);

        var user1 = new UserDTO();
        user1.setUsername("user1");
        user1.setEmail("user1@user1.com");
        user1.setPassword("user1");
        user1.setURL("https://images.pexels.com/photos/941693/pexels-photo-941693.jpeg?auto=compress&cs=tinysrgb&dpr=2&w=500");

        var user2 = new UserDTO();
        user2.setUsername("user2");
        user2.setEmail("user2@user2.com");
        user2.setPassword("user2");
        user2.setURL("https://images.pexels.com/photos/774909/pexels-photo-774909.jpeg?auto=compress&cs=tinysrgb&dpr=2&w=500");

        userDetailsService.registerUser(user1);
        userDetailsService.registerUser(user2);
    }
}
