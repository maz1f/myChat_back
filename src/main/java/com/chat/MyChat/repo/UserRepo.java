package com.chat.MyChat.repo;


import com.chat.MyChat.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepo extends CrudRepository<UserEntity, Long> {
    @Override
    List<UserEntity> findAll();
    Optional<UserEntity> findByUsername(String username);
}
