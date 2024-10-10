package com.e_commerce_singleVendorWithSpringBoot.Filter;

import com.e_commerce_singleVendorWithSpringBoot.ServiceImpl.CustomUserDetailsService;
import com.e_commerce_singleVendorWithSpringBoot.ServiceImpl.JwtService;
import org.apache.catalina.core.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private  JwtService jwtService; // CHANGED: Inject JwtService

    @Autowired
    private CustomUserDetailsService customUserDetailsService; // ADDED: Inject UserDetailsService

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String userName = null;

        // Log the presence of the Authorization header
        if (authHeader != null) {
            System.out.println("Authorization header found: " + authHeader);
        } else {
            System.out.println("Authorization header missing");
        }

        // Extract JWT token from header
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
            userName = jwtService.extractUserName(token); // ADDED: Extract username from token
            // Log extracted token and username
            System.out.println("Token: " + token);
            System.out.println("Username from token: " + userName);
        } else {
            System.out.println("Bearer token missing or malformed in Authorization header");
        }

        // Validate the token and set authentication context
        if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(userName);

            // Log the user details loading attempt
            if (userDetails != null) {
                System.out.println("UserDetails found for username: " + userName);
            } else {
                System.out.println("UserDetails not found for username: " + userName);
            }
            if (jwtService.validateToken(token, userDetails)) { // Token validation logic
                System.out.println("Token is valid.");
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authenticationToken); // ADDED: Set authentication
                // Log the success of authentication
                System.out.println("Authentication successful for user: " + userName);
            } else {
                System.out.println("JWT Token validation failed for token: " + token);
            }
        }
        filterChain.doFilter(request, response);
    }
}
