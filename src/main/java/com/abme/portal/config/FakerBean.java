package com.abme.portal.config;

import com.github.javafaker.Faker;
import org.springframework.context.annotation.Bean;

import java.util.Locale;

@org.springframework.context.annotation.Configuration
public class FakerBean {
    @Bean
    public Faker polishFaker() {
        return Faker.instance(new Locale("pl"));
    }
}
