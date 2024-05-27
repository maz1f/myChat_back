package com.chat.MyChat.service;

import com.chat.MyChat.entity.RefreshTokenEntity;
import com.chat.MyChat.exception.InvalidRefreshToken;
import com.chat.MyChat.exception.UserAlreadyExistException;
import com.chat.MyChat.repo.RefreshTokenRepo;
import com.chat.MyChat.repo.UserRepo;
import com.chat.MyChat.util.JwtTokenUtils;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Date;
import java.util.Optional;

@Service
@Slf4j
public class RefreshTokenService {
    @Autowired
    private RefreshTokenRepo refreshTokenRepo;
    @Autowired
    private UserRepo userRepository;
    @Value("${refreshToken.secret}")
    private String secret;
    @Value("${refreshToken.lifetime}")
    private Duration tokenLifeTime;
    @Autowired
    private JwtTokenUtils jwtTokenUtils;
    @Autowired
    private UserService userService;

    public String generateRefreshToken(String username) {
        Date now = new Date();
        Date expiredDate = new Date(now.getTime() + tokenLifeTime.toMillis());
        String token = Jwts.builder()
                        .setSubject(username)
                        .setExpiration(expiredDate)
                        .signWith(Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8)), SignatureAlgorithm.HS256)
                        .compact();
        RefreshTokenEntity refreshToken = RefreshTokenEntity.builder()
                .user(userRepository.findByUsername(username).get())
                .token(token)
                .expiredAt(expiredDate)
                .build();
        refreshTokenRepo.save(refreshToken);
        return token;
    }

    public Optional<RefreshTokenEntity> findByToken(String token) {
        return refreshTokenRepo.findByToken(token);
    }

    public boolean validateRefreshToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8)))
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException expEx) {
            log.error("Token expired", expEx);
        } catch (UnsupportedJwtException unsEx) {
            log.error("Unsupported jwt", unsEx);
        } catch (MalformedJwtException mjEx) {
            log.error("Malformed jwt", mjEx);
        } catch (Exception e) {
            log.error("invalid token", e);
        }
        return false;
    }

    public String refreshToken(String token) throws InvalidRefreshToken {
        Optional<RefreshTokenEntity> refreshToken = findByToken(token);
        if (refreshToken.isEmpty() || !validateRefreshToken(refreshToken.get().getToken()))
            throw new InvalidRefreshToken("Invalid refresh token!");
        return jwtTokenUtils.generateToken(
                userService.loadUserByUsername(
                        refreshToken.get().getUser().getUsername()
                )
        );
    }

    public boolean deleteRefreshToken(String token) {
        Optional<RefreshTokenEntity> refreshToken = findByToken(token);
        refreshToken.ifPresent(refreshTokenEntity -> refreshTokenRepo.delete(refreshTokenEntity));
        return true;
    }
}
