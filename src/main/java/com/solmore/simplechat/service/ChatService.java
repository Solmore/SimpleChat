package com.solmore.simplechat.service;

import com.solmore.simplechat.domain.chatmessage.ChatMessage;

import java.util.List;

public interface ChatService {

    void send(ChatMessage chatMessage);

    List<ChatMessage> getAllMessages();

    void delete(String username, String message);
}
