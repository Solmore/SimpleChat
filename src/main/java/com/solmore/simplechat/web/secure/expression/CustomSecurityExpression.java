package com.solmore.simplechat.web.secure.expression;

import com.solmore.simplechat.domain.chatmessage.ChatMessage;
import com.solmore.simplechat.domain.user.User;
import com.solmore.simplechat.domain.user.exception.UserNotFoundException;
import com.solmore.simplechat.repository.ChatRepository;
import com.solmore.simplechat.repository.UserRepository;
import com.solmore.simplechat.web.secure.SecureEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service("customSecurityExpression")
@RequiredArgsConstructor
public class CustomSecurityExpression {

    private final ChatRepository chatRepository;
    private final UserRepository userRepository;

    public boolean canAccessUser(final String username, final String message) {
        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();
        Object principal = authentication.getPrincipal();
        if (!(principal instanceof SecureEntity)) {
            throw new RuntimeException("Principal is not an instance of SecureEntity");
        }
        SecureEntity secureEntity = (SecureEntity) principal;
        if (!secureEntity.getUsername().equals(username)) {
            return false;
        }
        User user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UserNotFoundException("User not found.")
                );
        ChatMessage chatMessage = chatRepository.findBySenderUserAndContent(username,message);
        return chatRepository.isMessageOwner(user.getId(), chatMessage.getId());
    }
}
