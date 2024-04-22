package com.chat.MyChat.controller;

import com.chat.MyChat.entity.UserEntity;
import com.chat.MyChat.exception.UserAlreadyExistException;
import com.chat.MyChat.model.User;
import com.chat.MyChat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getUsers(){
        return userService.getAllUsers();
    }

    @PostMapping
    public ResponseEntity createUser(@RequestBody UserEntity user) {
        try {
            System.out.println(user);
            userService.registration(user);
            return ResponseEntity.ok("User registered");
        } catch (UserAlreadyExistException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        catch(Exception e) {
            return ResponseEntity.badRequest().body("There are something wrong....");
        }
    }
}
