package com.solmore.simplechat.web.mapper;

import com.solmore.simplechat.domain.user.User;
import com.solmore.simplechat.web.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {


    UserDto toDto(User entity);


    List<UserDto> toDto(List<User> entity);

    //@Mapping(source = "users", target = "", ignore = true)
    @Mapping(target = "id", ignore = true)
    User toEntity(UserDto dto);

    //@Mapping(source = "users", target = "", ignore = true)
    @Mapping(target = "id", ignore = true)
    List<User> toEntity(List<UserDto> dtos);
}
