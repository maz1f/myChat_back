package com.chat.MyChat.service;

import com.chat.MyChat.entity.UserEntity;
import com.chat.MyChat.exception.UserAlreadyExistException;
import com.chat.MyChat.model.CustomUserDetails;
import com.chat.MyChat.model.Role;
import com.chat.MyChat.model.User;
import com.chat.MyChat.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<User> getAllUsers(){
        return userRepo.findAll().stream().map(User::toModel).collect(Collectors.toList());
    }

    public boolean isUserExist(String username){
        return userRepo.findByUsername(username).isPresent();
    }

    public List<String> getAllUsernames(){
        return userRepo.findAll().stream().map(UserEntity::getUsername).collect(Collectors.toList());
    }

    public UserEntity registration(UserEntity user) throws UserAlreadyExistException {
        if (userRepo.findByUsername(user.getUsername()).isPresent())
            throw new UserAlreadyExistException("User already exist!");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setIsOnline(false);
        user.setRoles(Collections.singleton(Role.USER));
        return userRepo.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> user = userRepo.findByUsername(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException(username);
        }
        return new CustomUserDetails(user.get());
    }

}
