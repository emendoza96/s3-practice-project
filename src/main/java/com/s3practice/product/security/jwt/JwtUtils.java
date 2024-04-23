package com.s3practice.product.security.jwt;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.s3practice.product.service.TokenBlacklistService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtils {

    @Value("${jwt.secret.key}")
    private String secretKey;

    @Value("${jwt.time.expiration}")
    private String timeExpiration;

    @Autowired
    private TokenBlacklistService tokenBlacklistService;

    public String generateAccessToken(String username) {
        return Jwts.builder()
            .setSubject(username)
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(timeExpiration)))
            .signWith(getSignatureKey(), SignatureAlgorithm.HS256)
            .compact();
    }

    public boolean isTokenValid(String token) {
        try {
            Jwts.parserBuilder()
                .setSigningKey(getSignatureKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

                // The token must not be black listed
            return !tokenBlacklistService.isBlacklisted(token);
        } catch (Exception e) {
            return false;
        }
    }

    // Get token's username
    public String getUsernameFromToken(String token){
        return getClaim(token, Claims::getSubject);
    }

    // Get a specific Claim
    public <T> T getClaim(String token, Function<Claims, T> claimFunction) {
        Claims claims = extractAllClaims(token);
        return claimFunction.apply(claims);
    }

    // Get all token's Claims
    public Claims extractAllClaims(String token){
        return Jwts.parserBuilder()
        .setSigningKey(getSignatureKey())
        .build()
        .parseClaimsJws(token)
        .getBody();
    }

    public Key getSignatureKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
