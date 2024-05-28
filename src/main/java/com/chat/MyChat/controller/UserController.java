package com.chat.MyChat.controller;

import com.chat.MyChat.dto.AppError;
import com.chat.MyChat.dto.LoginRequest;
import com.chat.MyChat.dto.JwtResponse;
import com.chat.MyChat.dto.RefreshTokenRequest;
import com.chat.MyChat.entity.UserEntity;
import com.chat.MyChat.exception.InvalidRefreshToken;
import com.chat.MyChat.exception.UserAlreadyExistException;
import com.chat.MyChat.model.User;
import com.chat.MyChat.service.RefreshTokenService;
import com.chat.MyChat.service.UserService;
import com.chat.MyChat.util.JwtTokenUtils;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class UserController {
    private final UserService userService;
    private final JwtTokenUtils jwtTokenUtils;
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenService refreshTokenService;

    @GetMapping
    public List<User> getUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("/getName")
    public String index(@RequestHeader("Authorization") String token){
        System.out.println(token.substring(7));
        return jwtTokenUtils.getUsernameByToken(token.substring(7));
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody UserEntity user) {
        try {
            userService.registration(user);
            return ResponseEntity.ok("User registered");
        } catch (UserAlreadyExistException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        catch(Exception e) {
            return ResponseEntity.badRequest().body("There are something wrong....");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest authRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>(new AppError(HttpStatus.UNAUTHORIZED.value(), "Uncorrect login or password"), HttpStatus.UNAUTHORIZED);
        }
        UserDetails userDetails = userService.loadUserByUsername(authRequest.getUsername());
        return ResponseEntity.ok(
                JwtResponse.builder()
                        .token(jwtTokenUtils.generateToken(userDetails))
                        .refreshToken(refreshTokenService.generateRefreshToken(userDetails.getUsername()))
                        .build()
        );
    }

    @PostMapping("/refreshToken")
    public JwtResponse refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) throws InvalidRefreshToken {
        return JwtResponse.builder()
                .token(refreshTokenService.refreshToken(refreshTokenRequest.getToken()))
                .refreshToken(refreshTokenRequest.getToken())
                .build();
    }

    @PostMapping("/customLogout")
    public ResponseEntity<?> logout(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        System.out.println(refreshTokenRequest.getToken());
        refreshTokenService.deleteRefreshToken(refreshTokenRequest.getToken());
        return ResponseEntity.ok("ok");
    }



}
