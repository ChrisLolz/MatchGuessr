package com.chris.backend.security.jwt;

import java.util.Date;

import javax.crypto.SecretKey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.chris.backend.security.services.AccDetailsImpl;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${jwt.secret}")
    private String SECRET;

    private int jwtExpirationMs = 86400000;

    public String generateJwtToken(Authentication authentication) {
        System.out.println("generateJwtToken");
        AccDetailsImpl userPrincipal = (AccDetailsImpl) authentication.getPrincipal();

        return Jwts.builder()
            .subject((userPrincipal.getUsername()))
            .issuedAt(new Date())
            .expiration(new Date((new Date()).getTime() + jwtExpirationMs))
            .signWith(key(),Jwts.SIG.HS512)
            .compact();
    }

    private SecretKey key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET));
    }

    public String getUsernameFromJwtToken(String token) {
        return Jwts.parser()
            .verifyWith(key()).build()
            .parseSignedClaims(token)
            .getPayload().getSubject();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().verifyWith(key()).build().parseSignedClaims(authToken);
            return true;
        } catch (Exception e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        }

        return false;
    }
}
