package com.chat.MyChat.util;

import com.chat.MyChat.model.CustomUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class JwtTokenUtils {
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.lifetime}")
    private Duration jwtLifetime;

    //private final Key key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));

    public String generateToken(UserDetails userDetails) {
         Map<String, Object> claims = new HashMap<>();
         Set<String> roles = userDetails.getAuthorities().stream()
                 .map(GrantedAuthority::getAuthority)
                 .collect(Collectors.toSet());
         claims.put("roles", roles);

         Date issuedDate = new Date();
         Date expiredDate = new Date(issuedDate.getTime() + jwtLifetime.toMillis());
         Key key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
         return Jwts.builder()
                 .setClaims(claims)
                 .setSubject(userDetails.getUsername())
                 .setIssuedAt(issuedDate)
                 .setExpiration(expiredDate)
                 .signWith(key, SignatureAlgorithm.HS256)
                 .compact();
    }

    public String getUsername(String token) {
        return getClaimsFromToken(token).getSubject();
    }

    public Set<String> getRoles(String token) {
        return getClaimsFromToken(token).get("roles", Set.class);
    }

    private Claims getClaimsFromToken(String token) {
        Key key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

}
