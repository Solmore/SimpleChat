package com.solmore.simplechat.serviceimpl;

import com.solmore.simplechat.config.TestConfig;
import com.solmore.simplechat.domain.chatmessage.ChatMessage;
import com.solmore.simplechat.domain.user.User;
import com.solmore.simplechat.repository.ChatRepository;
import com.solmore.simplechat.repository.UserRepository;
import com.solmore.simplechat.service.ChatServiceImpl;
import com.solmore.simplechat.service.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@Import(TestConfig.class)
@ExtendWith(MockitoExtension.class)
public class ChatServiceImplTest {

    @MockBean
    private ChatRepository chatRepository;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private ChatServiceImpl chatService;

    @Test
    void testSend() {
        // Given
        ChatMessage chatMessage = new ChatMessage(
                null,
                "testUser",
                "test message",
                LocalDateTime.now()
        );
        User testUser = new User();
        testUser.setId(1L);
        Mockito.when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(testUser));
        chatService.send(chatMessage);
        Mockito.verify(chatRepository, Mockito.times(1)).save(chatMessage);
        Mockito.verify(chatRepository, Mockito.times(1)).assignMessage(testUser.getId(), chatMessage.getId());
    }

    @Test
    void testGetAllMessages() {
        List<ChatMessage> expected = Arrays.asList(new ChatMessage(), new ChatMessage());
        Mockito.when(chatRepository.findAll()).thenReturn(expected);
        List<ChatMessage> actual = chatService.getAllMessages();
        Assertions.assertSame(expected, actual);
        Mockito.verify(chatRepository).findAll();
    }

    @Test
    void testDelete() {
        String username = "john";
        String message = "hi";
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setSenderUser(username);
        chatMessage.setContent(message);
        Mockito.when(chatRepository.findBySenderUserAndContent(username, message))
                .thenReturn(chatMessage);
        chatService.delete(username, message);
        Mockito.verify(chatRepository).findBySenderUserAndContent(username, message);
        Mockito.verify(chatRepository).delete(chatMessage);
    }
}
