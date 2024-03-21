package com.solmore.simplechat.serviceimpl;

import com.solmore.simplechat.config.TestConfig;
import com.solmore.simplechat.domain.user.User;
import com.solmore.simplechat.domain.user.exception.UserNotFoundException;
import com.solmore.simplechat.repository.UserRepository;
import com.solmore.simplechat.service.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@Import(TestConfig.class)
@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserServiceImpl userService;

    @Test
    void testGetByUsername() {
        String username = "john";
        User expected = new User();
        expected.setUsername(username);
        Mockito.when(userRepository.findByUsername(username))
                .thenReturn(Optional.of(expected));
        User actual = userService.getByUsername(username);
        Assertions.assertSame(expected, actual);
        Mockito.verify(userRepository).findByUsername(username);
    }

    @Test
    void testGetByUsernameNotFound() {
        Mockito.when(userRepository.findByUsername("invalid"))
                .thenReturn(Optional.empty());
        Assertions.assertThrows(UserNotFoundException.class, () -> {
            userService.getByUsername("invalid");
        });
    }

    @Test
    void testCreateUser() {
        String username = "testUser";
        String password = "password";
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        Mockito.when(userRepository.findByUsername(username)).thenReturn(Optional.empty());
        Mockito.when(passwordEncoder.encode(password)).thenReturn("encodedPassword");
        userService.createUser(user);
        Mockito.verify(userRepository, Mockito.times(1)).findByUsername(username);
        Mockito.verify(passwordEncoder, Mockito.times(1)).encode(password);
        Mockito.verify(userRepository, Mockito.times(1)).save(user);

    }


}
