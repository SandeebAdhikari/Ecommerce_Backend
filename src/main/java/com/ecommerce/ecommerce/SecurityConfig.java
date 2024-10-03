package com.ecommerce.ecommerce;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())  // Disable CSRF for testing purposes
                .authorizeHttpRequests(authorize ->
                        authorize
                                .requestMatchers("/api/users/register", "/api/users/all").permitAll()  // Allow these endpoints without authentication
                                .anyRequest().authenticated()  // Require authentication for all other endpoints
                )
                .anonymous();  // Allow anonymous access
        return http.build();
    }
}
