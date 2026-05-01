package com.universidad.proyecto.findtrack.security;

import org.springframework.stereotype.Component;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
    
    @Value("${jwt.secret}")
    private String secret;

    public String generateToken(UUID id) {
        return Jwts.builder()
                .setSubject(id.toString())
                .setExpiration(new java.util.Date(System.currentTimeMillis() + (3600000*24))) 
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()), SignatureAlgorithm.HS256)
                .compact();
    }

}
