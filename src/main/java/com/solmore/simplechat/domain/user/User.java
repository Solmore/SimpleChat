package com.solmore.simplechat.domain.user;

import com.solmore.simplechat.domain.chatmessage.ChatMessage;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String fullname;
    private String password;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_chats", schema = "simplechatdbschema",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "chat_id")
    )
    private List<ChatMessage> chatMessages;
}
