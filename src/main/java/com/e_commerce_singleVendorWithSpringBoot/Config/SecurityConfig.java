package com.e_commerce_singleVendorWithSpringBoot.Config;

import com.e_commerce_singleVendorWithSpringBoot.Filter.JwtFilter;
import com.e_commerce_singleVendorWithSpringBoot.ServiceImpl.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;



@Configuration
@EnableWebSecurity
public class SecurityConfig {


    @Autowired
   private CustomUserDetailsService customUserDetailsService;
    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(customizer -> customizer.disable())  // Disable CSRF for API-based applications
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/users/register", "/api/users/login", "/api/products/view/all","/api/products/view/{id}","/api/admin/reset-password")
                        .permitAll()  // Allow public access to register and login
                        .requestMatchers("/api/admin/promote/**", "/api/users/delete/all").hasRole("ADMIN")  // Restrict to admin only
                        .anyRequest().authenticated()  // All other requests require authentication
                )
                .httpBasic(Customizer.withDefaults())  // Use HTTP Basic authentication
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
              return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider  provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(new BCryptPasswordEncoder(10));
        provider.setUserDetailsService(customUserDetailsService);
        return provider;
    }
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);  // Strength 10 is recommended
    }
}
