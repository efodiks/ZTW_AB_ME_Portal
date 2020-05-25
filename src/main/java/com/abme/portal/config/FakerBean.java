package com.abme.portal.config;

import com.github.javafaker.Faker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Locale;

@Configuration
@Profile("dev")
public class FakerBean {
    @Bean
    public Faker polishFaker() {
        return Faker.instance(new Locale("pl"));
    }
}
