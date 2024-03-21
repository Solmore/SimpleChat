package com.solmore.simplechat.repository;

import com.solmore.simplechat.domain.chatmessage.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRepository extends JpaRepository<ChatMessage,Long> {

    ChatMessage findBySenderUserAndContent(String username, String message);

    @Modifying
    @Query(value = """
            INSERT INTO users_chats (user_id, chat_id)
            VALUES (:userId, :chatId)
            """, nativeQuery = true)
    void assignMessage(
            @Param("userId") Long userId,
            @Param("chatId") Long chatId
    );

    @Query(value = """
             SELECT exists(
                           SELECT 1
                           FROM users_chats
                           WHERE user_id = :userId
                             AND chat_id = :chatId)
            """, nativeQuery = true)
    boolean isMessageOwner(@Param("userId") Long userId,
                         @Param("chatId") Long chatId);
}
