package com.chat.MyChat.model;

import com.chat.MyChat.entity.UserEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class User {
    private Long id;
    private String username;
    private Boolean isOnline;

    public static User toModel(UserEntity entity) {
        User model = new User();
        model.setId(entity.getId());
        model.setUsername(entity.getUsername());
        model.setIsOnline(entity.getIsOnline());
        return model;
    }

}
