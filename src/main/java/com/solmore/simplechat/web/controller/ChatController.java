package com.solmore.simplechat.web.controller;

import com.solmore.simplechat.domain.chatmessage.ChatMessage;
import com.solmore.simplechat.service.ChatService;
import com.solmore.simplechat.web.dto.ChatMessageDto;
import com.solmore.simplechat.web.mapper.ChatMessageMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping
@RequiredArgsConstructor
@Slf4j
public class ChatController {

    private final ChatService chatService;
    private final ChatMessageMapper chatMessageMapper;
    private final Logger applogger = LoggerFactory
            .getLogger(Logger.ROOT_LOGGER_NAME);

    @PostMapping("/message/{username}/send")
    @ResponseStatus(HttpStatus.CREATED)
    public void send(@PathVariable
                     final String username,
                     @RequestBody
                     final String message) {
        applogger.info("ChatController send message to chat by {}",
                username);
        ChatMessage chat = new ChatMessage(
                null,
                username,
                message,
                LocalDateTime.now());
        chatService.send(chat);
    }

    @GetMapping("/message")
    @ResponseStatus(HttpStatus.OK)
    public List<ChatMessageDto> getMessage() {
        applogger.info("Controller get all message");
        List<ChatMessage> chatMessages = chatService.getAllMessages();
        return chatMessageMapper.toDto(chatMessages);
    }

    @PostMapping("/message/{username}/delete")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("@customSecurityExpression.canAccessUser(#username,#message)")
    public void delete(@PathVariable
                       final String username,
                       @RequestBody final String message) {
        applogger.info("Controller method delete the message by {}", username);
        chatService.delete(username, message);
    }

}
