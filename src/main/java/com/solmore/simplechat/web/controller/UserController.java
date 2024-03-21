package com.solmore.simplechat.web.controller;

import com.solmore.simplechat.domain.user.User;
import com.solmore.simplechat.service.UserService;
import com.solmore.simplechat.web.dto.UserDto;
import com.solmore.simplechat.web.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    private final Logger applogger = LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);

    @PostMapping("/user")
    @ResponseStatus(HttpStatus.CREATED)
    public void createUser(@RequestBody final UserDto userDto) {
        applogger.info("Controller method to create a user");
        User user = userMapper.toEntity(userDto);
        userService.createUser(user);
    }
}
