package com.workflowpro.backend.security;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

    private final SecretKey key=Keys.hmacShaKeyFor
    ("ThisIsASecretKeyForWorkflowProJwtAuthentication123456".getBytes());

    private final long expiration=1000*60*60*24;

    public String generateToken(String username,String role){
        return Jwts.builder().subject(username).claim("role", role).issuedAt(new Date())
        .expiration(new Date(System.currentTimeMillis()+expiration)).signWith(key).compact();
    }

    public String extractUsername(String token){
        return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload().getSubject();
    }


}
