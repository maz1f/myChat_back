package com.chat.MyChat.service;

import com.chat.MyChat.entity.UserEntity;
import com.chat.MyChat.exception.UserAlreadyExistException;
import com.chat.MyChat.model.User;
import com.chat.MyChat.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<User> getAllUsers(){
        return userRepo.findAll().stream().map(User::toModel).collect(Collectors.toList());
    }

    public UserEntity registration(UserEntity user) throws UserAlreadyExistException {
        if (userRepo.findByUsername(user.getUsername()) != null)
            throw new UserAlreadyExistException("User already exist!");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setIsOnline(false);
        return userRepo.save(user);
    }

}
