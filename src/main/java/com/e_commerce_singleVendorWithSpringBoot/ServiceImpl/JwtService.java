package com.e_commerce_singleVendorWithSpringBoot.ServiceImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


@Service
public class JwtService {

    private String signature = "myVerySecureSuperLongSignatureKeyHere";


    public String generateToken(String email) {
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder()
                .setClaims(claims) // Correct method for setting claims
                .setSubject(email)
                .setIssuedAt(new Date(System.currentTimeMillis())) // Issue time
                // Set token expiration to 24 hours (1000 ms * 60 sec * 60 min * 24 hours)
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                .signWith(getSigningKey()) // Signing with secure key
                .compact();
    }

    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(getSigningKey()) // Use your secret key
                .build()
                .parseClaimsJws(token)
                .getBody(); // Extract the claims (body of the JWT)
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String userName = extractUserName(token);
        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }
    // Generate the signing key
    private SecretKey getSigningKey() {
       // byte[] key = Base64.getDecoder().decode(signature); // Decode the base64-encoded secret
        byte[] key = signature.getBytes();
        return Keys.hmacShaKeyFor(key); // Generate the HMAC SHA key
    }
}
