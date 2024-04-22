package com.chat.MyChat.repo;

import com.chat.MyChat.entity.MessageEntity;
import org.springframework.data.repository.CrudRepository;

public interface MessageRepo extends CrudRepository<MessageEntity, Long> {

}
