package com.solmore.simplechat.web.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ChatMessageDto {

    private String senderUser;
    private String content;
    private LocalDateTime timestamp;
}
