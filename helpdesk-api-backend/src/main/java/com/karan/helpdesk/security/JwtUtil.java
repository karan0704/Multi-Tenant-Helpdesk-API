package com.karan.helpdesk.security;

import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

@Component
public class JwtUtil {

    private final String SECRET = "mysecretkey";
    private final long EXPIRATION_MS = 86400000; // 1 day

    public String generateToken(String email, String role, UUID tenantId) {
        return Jwts.builder()
                .setSubject(email)
                .claim("role", role)
                .claim("tenantId", tenantId.toString())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_MS))
                .signWith(SignatureAlgorithm.HS256, SECRET)
                .compact();
    }

    public String getEmailFromToken(String token) {
        return parse(token).getBody().getSubject();
    }

    public Claims getAllClaims(String token) {
        return parse(token).getBody();
    }

    public boolean validateToken(String token) {
        try {
            parse(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    private Jws<Claims> parse(String token) {
        return Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token);
    }
}
