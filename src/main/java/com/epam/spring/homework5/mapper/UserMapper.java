package com.epam.spring.homework5.mapper;

import com.epam.spring.homework5.dto.UserDto;
import com.epam.spring.homework5.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User mapUserDtoToUser(UserDto userDto);

    UserDto mapUserToUserDto(User user);
}
