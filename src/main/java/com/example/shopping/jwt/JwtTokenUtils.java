package com.example.shopping.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.Instant;
import java.util.Date;

@Slf4j
@Component
public class JwtTokenUtils {
    // 암호키
    private final Key signingKey;

    public JwtTokenUtils(
            @Value("${jwt.secret}")
            String jwtSecret
    ) {
       this.signingKey
               = Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

    // JWT 발급 메소드
    public String generateToken(UserDetails userDetails) {
        Claims jwtClaims = Jwts.claims()
                // 사용자
                .setSubject(userDetails.getUsername())
                // 발급일자
                .setIssuedAt(Date.from(Instant.now()))
                // 만료일자 (24시간)
                .setExpiration(Date.from(Instant.now().plusSeconds(60 * 60 * 24)));

        return Jwts.builder()
                .setClaims(jwtClaims)
                .signWith(signingKey)
                .compact(); // 문자열로 반환
    }
}
