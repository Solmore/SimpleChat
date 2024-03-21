package com.solmore.simplechat.web.secure;

import com.solmore.simplechat.domain.user.User;

public final class SecureEntityFactory {

    public static SecureEntity create(final User user) {
        return new SecureEntity(
                user.getId(),
                user.getUsername(),
                user.getFullname(),
                user.getPassword()
        );
    }
}
