package com.gdg.illum.user.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class UserDetailsServiceConfig {
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.withUsername("validUser")
                .password("{noop}validPassword")
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(user);
    }
}
