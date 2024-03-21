package com.solmore.simplechat.service;

import com.solmore.simplechat.domain.user.User;

public interface UserService {

    User getByUsername(String username);

    void createUser(User user);
}
