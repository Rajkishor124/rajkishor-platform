package com.rajkishor.platform.common.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${security.jwt.secret}")
    private String secret;

    @Value("${security.jwt.access-token-expiry-ms}")
    private long accessTokenExpiryMs;

    private SecretKey key;

    /**
     * Initialize the signing key once at startup.
     * Enforces minimum secret length (HS256 requirement).
     */
    @PostConstruct
    private void init() {
        if (secret.length() < 32) {
            throw new IllegalStateException(
                    "JWT secret must be at least 32 characters long"
            );
        }
        this.key = Keys.hmacShaKeyFor(
                secret.getBytes(StandardCharsets.UTF_8)
        );
    }

    public String generateToken(String email) {
        return Jwts.builder()
                .subject(email)
                .issuedAt(new Date())
                .expiration(
                        new Date(System.currentTimeMillis() + accessTokenExpiryMs)
                )
                .signWith(key) // algorithm inferred (HS256)
                .compact();
    }

    public String extractEmail(String token) {
        return parseClaims(token).getSubject();
    }

    private Claims parseClaims(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
