package com.e_commerce_singleVendorWithSpringBoot.ServiceImpl;

import com.e_commerce_singleVendorWithSpringBoot.Entity.User;
import com.e_commerce_singleVendorWithSpringBoot.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    // Implement the loadUserByUsername method that Spring Security expects
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return loadUserByEmail(email);
    }

    // Custom method to load user by email for authentication
    public UserDetails loadUserByEmail(String email) throws UsernameNotFoundException {
        // Fetch the user by email from the repository
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (!userOptional.isPresent()) {
            System.out.println("User not found for email: " + email); // Log if user is not found
            throw new UsernameNotFoundException("User not found with email: " + email);
        }

        User user = userOptional.get();
        System.out.println("User found: " + user.getUserName()); // Log if user is found
        System.out.println("Role from database: " + user.getRole());  // Log the user's role


        // Create a list of authorities (roles) based on the user's role
        List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()));

        // Return a custom UserDetails implementation with authorities
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),  // Email as username
                user.getPassword(),  // Encrypted password
                authorities  // Authorities or roles are passed here
        );
    }
}