package org.example.taskmanagement.mapper;

import org.example.taskmanagement.dto.UserCreationRequestDto;
import org.example.taskmanagement.dto.UserDto;
import org.example.taskmanagement.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreationRequestDto userCreationRequestDto);
    UserDto toUserDto(User user);
}
