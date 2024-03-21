package com.solmore.simplechat.web.mapper;

import com.solmore.simplechat.domain.chatmessage.ChatMessage;
import com.solmore.simplechat.web.dto.ChatMessageDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ChatMessageMapper {

    ChatMessageDto toDto(ChatMessage entity);

    List<ChatMessageDto> toDto(List<ChatMessage> entity);

    @Mapping(target = "id", ignore = true)
    ChatMessage toEntity(ChatMessageDto dto);

    @Mapping(target = "id", ignore = true)
    List<ChatMessage> toEntity(List<ChatMessageDto> dtos);
}
