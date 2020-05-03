package com.abme.portal.config;

import com.abme.portal.infrastracture.security.JwtFilter;
import com.abme.portal.infrastracture.security.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final TokenProvider tokenProvider;
    private final SecurityProperties securityProperties;

    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf().disable()
                .headers().frameOptions().disable()
                .and()
                .cors()
                .and()
                .authorizeRequests()
                .antMatchers("/api/posts/**").permitAll()
                .antMatchers("/api/authenticate").permitAll()
                .antMatchers("/api/users/**").permitAll()
                .antMatchers("/api/register").permitAll()
                .antMatchers("/api/**").authenticated()
                .and()
                .addFilterBefore(new JwtFilter(tokenProvider, securityProperties), UsernamePasswordAuthenticationFilter.class)
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
}
