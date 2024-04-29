package com.chat.MyChat.entity;

import com.chat.MyChat.model.Role;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Entity
@NoArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    private String username;

    @Setter
    private String password;

    @Setter
    private String email;

    @Setter
    private Boolean isOnline;

    @Setter
    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sender")
    private List<MessageEntity> sentMessage;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recipient")
    private List<MessageEntity> receivedMessages;

    public void addSetMessage(MessageEntity message) {
        this.sentMessage.add(message);
    }

    public void addReceivedMessage(MessageEntity message) {
        this.receivedMessages.add(message);
    }

}
