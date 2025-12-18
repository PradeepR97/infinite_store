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

    // ✅ Generate token AFTER OTP verification
    public String generateToken(Long userId, String phoneNumber) {

        long expiration = 1000 * 60 * 60 * 24; // 24 hours

        return Jwts.builder()
                .setSubject(phoneNumber)
                .claim("userId", userId)          // ✅ STORE USER ID
                .claim("phone", phoneNumber)
                .setIssuer("InfiniteStore")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    // ✅ Extract userId from token (Used by Cart)
    public Long extractUserId(String token) {

        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)       // ✅ CORRECT SECRET
                .parseClaimsJws(token)
                .getBody();

        return claims.get("userId", Long.class);
    }

    // (Optional but useful)
    public String extractPhoneNumber(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }
}
