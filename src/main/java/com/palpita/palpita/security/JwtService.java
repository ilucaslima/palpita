package com.palpita.palpita.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {
  private final String SECRET = "palpita-secret-key";

  private SecretKey getKey() {
    return Keys.hmacShaKeyFor(SECRET.getBytes());
  }


  public String generateToken(String username){
    return Jwts.builder()
        .subject(username)
        .issuedAt(new Date())
        .expiration(new Date(System.currentTimeMillis() + 60 * 60 * 24)) // 24 hours
        .signWith(getKey())
        .compact();
  }

  public String extractUsername(String token){
    Claims claims = Jwts.parser()
        .verifyWith(getKey())
        .build()
        .parseSignedClaims(token)
        .getPayload();

    return claims.getSubject();
  };
}