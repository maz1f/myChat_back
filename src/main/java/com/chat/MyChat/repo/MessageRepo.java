package com.chat.MyChat.repo;

import com.chat.MyChat.entity.MessageEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MessageRepo extends CrudRepository<MessageEntity, Long> {
    List<MessageEntity> getAllBySenderUsernameAndRecipientUsername(String senderUsername, String recipientUsername);
}
