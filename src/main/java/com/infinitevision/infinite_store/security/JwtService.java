package com.infinitevision.infinite_store.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {

    private static final String SECRET_KEY =
            "uH9vJx8XH3QpLe2I1Q9Yw7LmNc8Tf4QsZpR+8r5b0Ks=";


    public String generateToken(Long userId, String phoneNumber) {
        long expiration = 1000 * 60 * 60 * 24; // 24 hours

        return Jwts.builder()
                .setSubject(phoneNumber)
                .claim("userId", userId)
                .claim("phone", phoneNumber)
                .setIssuer("InfiniteStore")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    // ---------------- Temporary token after OTP verification ----------------
    public String generateTempToken(String phoneNumber) {
        long expiration = 1000 * 60 * 15; // 15 minutes temporary token

        return Jwts.builder()
                .setSubject(phoneNumber)
                .claim("temp", true) // mark this as temp token
                .setIssuer("InfiniteStore")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public String extractPhoneFromTempToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();

        Boolean isTemp = claims.get("temp", Boolean.class);
        if (isTemp == null || !isTemp) {
            throw new RuntimeException("Invalid temporary token");
        }

        return claims.getSubject();
    }

    // Extract userId from normal token
    public Long extractUserId(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();

        return claims.get("userId", Long.class);
    }

    // Extract phone from normal token
    public String extractPhoneNumber(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }
}
