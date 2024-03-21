package com.solmore.simplechat.service;

import com.solmore.simplechat.domain.user.User;
import com.solmore.simplechat.domain.user.exception.UserNotFoundException;
import com.solmore.simplechat.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    private final Logger applogger = LoggerFactory
            .getLogger(Logger.ROOT_LOGGER_NAME);

    @Override
    public User getByUsername(String username) {
        applogger.info("UserService get user by username");
        return repository.findByUsername(username)
                .orElseThrow(() ->
                        new UserNotFoundException("User not found.")
                );
    }

    @Override
    public void createUser(User user) {
        if (repository.findByUsername(user.getUsername()).isPresent()) {
            throw new IllegalStateException("User already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        applogger.info("UserService save user");
        repository.save(user);
    }
}
