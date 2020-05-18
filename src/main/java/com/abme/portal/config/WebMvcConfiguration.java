package com.abme.portal.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;


@Configuration
public class WebMvcConfiguration {

    @Bean
    public WebMvcConfigurer webMvcConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
                configurer.defaultContentType(MediaType.APPLICATION_JSON);
            }
        };
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        var allowedMethods = List.of("HEAD", "GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS");
        var configuration = new CorsConfiguration().applyPermitDefaultValues();
        configuration.setAllowedMethods(allowedMethods);
        var configurationSource = new UrlBasedCorsConfigurationSource();
        configurationSource.registerCorsConfiguration("/api/**", configuration);
        return configurationSource;
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder.build();
    }
}
