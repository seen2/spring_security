package com.spring_security.spring_security.service;

import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JWTService {

    private String secretKey = null;

    public JWTService() throws NoSuchAlgorithmException {
        generateKey();
    }

    private void generateKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
        this.secretKey = Base64.getEncoder().encodeToString(keyGen.generateKey().getEncoded());
    }

    // generate JWT Token
    public String generateToken(String username) {
        if (username == null) {
            throw new NullPointerException("username cannot be null");
        }

        if (secretKey == null) {
            throw new IllegalStateException("secretKey is not initialized");
        }

        Map<String, Object> claims = new HashMap<>();
        claims.put("role", "admin");// can add more data
        return Jwts.builder().claims().add(claims).subject(username).issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24)).and().signWith(getKey())
                .compact();
    }

    // get key
    private SecretKey getKey() {
        byte[] keyBytes = Base64.getDecoder().decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // extract username from token
    public String extractUsername(String token) {
        // extract claims
        String username = null;
        if (token != null) {
            username = extractClaim(token, Claims::getSubject);
        }
        return username;
    }

    public Claims extractClaims(String token) {
        return Jwts.parser().verifyWith(getKey()).build().parseSignedClaims(token).getPayload();
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractClaims(token);
        return claimResolver.apply(claims);
    }

    private boolean isTokenExpired(String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }

    // validate token
    public boolean isTokenValid(String token, UserDetails userDetails) {

        return (userDetails.getUsername().equals(extractUsername(token)) && !isTokenExpired(token));
    }

}
