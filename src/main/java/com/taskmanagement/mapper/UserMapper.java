package com.taskmanagement.mapper;

import com.taskmanagement.dto.UserCreationRequestDto;
import com.taskmanagement.dto.UserDto;
import com.taskmanagement.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreationRequestDto userCreationRequestDto);
    UserDto toUserDto(User user);
}
