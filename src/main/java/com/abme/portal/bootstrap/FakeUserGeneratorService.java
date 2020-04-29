package com.abme.portal.bootstrap;

import com.abme.portal.domain.Authority;
import com.abme.portal.domain.User;
import com.abme.portal.exceptions.AuthorityNotFoundException;
import com.abme.portal.repository.AuthorityRepository;
import com.abme.portal.repository.UserRepository;
import com.abme.portal.security.AuthoritiesConstants;
import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class FakeUserGeneratorService {
    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;
    private final PasswordEncoder passwordEncoder;
    private final Faker faker;

    public void insertFakeUsers(int count) {
        var userAuthoritySingleton = findAuthority();
        var users = Stream
                .generate(() -> generateUser(userAuthoritySingleton))
                .limit(count)
                .collect(Collectors.toList());
         userRepository.saveAll(users);
    }

    private Set<Authority> findAuthority() {
        var authority = authorityRepository.findById(AuthoritiesConstants.USER).orElseThrow(AuthorityNotFoundException::new);
        return Collections.singleton(authority);
    }

    public User generateUser(Set<Authority> authoritySet) {
        var firstName = faker.name().firstName();
        var firstNameLowerCaseStripped = StringUtils.stripAccents(firstName).toLowerCase();
        var lastName = faker.name().lastName();
        var lastNameLowerCaseStripped = StringUtils.stripAccents(lastName).toLowerCase();

        var username = firstNameLowerCaseStripped + "." + lastNameLowerCaseStripped  ;
        var email = username + "@example.com";


        return new User()
                .setEmail(email)
                .setPasswordHash(passwordEncoder.encode(faker.internet().password()))
                .setFirstName(firstName)
                .setLastName(lastName)
                .setUsername(username)
                .setURL(faker.internet().avatar())
                .setAuthorities(authoritySet);
    }
}
