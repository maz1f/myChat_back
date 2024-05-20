package com.chat.MyChat.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class JwtTokenUtils {
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.lifetime}")
    private Duration jwtLifetime;

    public String generateToken(UserDetails userDetails) {
         Map<String, Object> claims = new HashMap<>();
         Set<String> roles = userDetails.getAuthorities().stream()
                 .map(GrantedAuthority::getAuthority)
                 .collect(Collectors.toSet());
         claims.put("roles", roles);

         Date issuedDate = new Date();
         Date expiredDate = new Date(issuedDate.getTime() + jwtLifetime.toMillis());
         return Jwts.builder()
                 .setClaims(claims)
                 .setSubject(userDetails.getUsername())
                 .setIssuedAt(issuedDate)
                 .setExpiration(expiredDate)
                 .signWith(Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8)), SignatureAlgorithm.HS256)
                 .compact();
    }

    public String getUsername(String token) {
        return getClaimsFromToken(token).getSubject();
    }

    public List<String> getRoles(String token) {
        return getClaimsFromToken(token).get("roles", List.class);
    }

    private Claims getClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8)))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String getUsernameByToken(String token){
        Claims claims = getClaimsFromToken(token);
        return claims.getSubject();
    }

}
