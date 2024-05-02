package com.chat.MyChat.model;

import com.chat.MyChat.entity.RefreshTokenEntity;
import com.chat.MyChat.entity.UserEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
public class RefreshToken {
    private String token;
    private Date expiredAt;
    private User user;

    public static RefreshToken toModel(RefreshTokenEntity refreshTokenEntity) {
        RefreshToken model = new RefreshToken();
        model.setToken(refreshTokenEntity.getToken());
        model.setExpiredAt(refreshTokenEntity.getExpiredAt());
        model.setUser(User.toModel(refreshTokenEntity.getUser()));
        return model;
    }
}
