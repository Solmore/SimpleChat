package com.solmore.simplechat.config;


import com.solmore.simplechat.repository.ChatRepository;
import com.solmore.simplechat.repository.UserRepository;
import com.solmore.simplechat.service.ChatServiceImpl;
import com.solmore.simplechat.service.UserServiceImpl;
import com.solmore.simplechat.web.secure.CustomeUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@TestConfiguration
@RequiredArgsConstructor
public class TestConfig {

    @Bean
    @Primary
    public BCryptPasswordEncoder testPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(
            final UserRepository userRepository
    ) {
        return new CustomeUserDetailsService(userRepository);
    }

    @Bean
    @Primary
    public UserServiceImpl userService(
            final UserRepository userRepository
    ) {
        return new UserServiceImpl(
                userRepository,
                testPasswordEncoder()
        );
    }

    @Bean
    @Primary
    public ChatServiceImpl chatService(
            final ChatRepository chatRepository,
            final UserRepository userRepository
            ) {
        return new ChatServiceImpl(chatRepository,userRepository);
    }


    @Bean
    public UserRepository userRepository() {
        return Mockito.mock(UserRepository.class);
    }

    @Bean
    public ChatRepository chatRepository(){ return Mockito.mock(ChatRepository.class); }

}
