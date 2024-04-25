package com.chat.MyChat.repo;


import com.chat.MyChat.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepo extends CrudRepository<UserEntity, Long> {
    @Override
    List<UserEntity> findAll();
    UserEntity findByUsername(String username);
}
