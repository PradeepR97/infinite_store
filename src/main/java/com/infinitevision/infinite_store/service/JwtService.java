package com.infinitevision.infinite_store.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {

    private static final String SECRET_KEY = "uH9vJx8XH3QpLe2I1Q9Yw7LmNc8Tf4QsZpR+8r5b0Ks="; // VALID BASE64

    public static String generateToken(String phoneNumber) {

        long expiration = 1000 * 60 * 60 * 24;

        return Jwts.builder()
                .setSubject(phoneNumber)
                .setIssuer("InfiniteStore")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }
}
