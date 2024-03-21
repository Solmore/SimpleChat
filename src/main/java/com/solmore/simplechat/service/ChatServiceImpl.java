package com.solmore.simplechat.service;

import com.solmore.simplechat.domain.chatmessage.ChatMessage;
import com.solmore.simplechat.domain.user.exception.UserNotFoundException;
import com.solmore.simplechat.repository.ChatRepository;
import com.solmore.simplechat.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatServiceImpl implements ChatService{

    private final ChatRepository chatRepository;
    private final UserRepository userRepository;
    private final Logger applogger = LoggerFactory
            .getLogger(Logger.ROOT_LOGGER_NAME);

    @Override
    @Transactional
    public void send(ChatMessage chatMessage) {
        applogger.info("ChatService save a message");
        chatRepository.save(chatMessage);
        Long userId = userRepository.findByUsername(chatMessage.getSenderUser()).orElseThrow(
                () -> new UserNotFoundException("User not found.")
        ).getId();
        chatRepository.assignMessage(userId,chatMessage.getId());
    }

    @Override
    public List<ChatMessage> getAllMessages() {
        applogger.info("ChatService get all messages");
        return chatRepository.findAll();
    }

    @Override
    public void delete(String username,String message) {
        applogger.info("ChatService delete a message");
        ChatMessage chatMessage = chatRepository.findBySenderUserAndContent(username, message);
        //TO-DO: add method to check only the last chatMessage for the username and the message
        chatRepository.delete(chatMessage);
    }
}
