package com.spring_security.spring_security.service;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.KeyGenerator;

import org.springframework.stereotype.Service;

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

    //get key
    private Key getKey() {
        byte[] keyBytes = Base64.getDecoder().decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
