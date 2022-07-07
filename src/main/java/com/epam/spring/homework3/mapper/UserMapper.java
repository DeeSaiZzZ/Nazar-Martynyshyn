package com.epam.spring.homework3.mapper;

import com.epam.spring.homework3.dto.UserDto;
import com.epam.spring.homework3.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User mapUserDtoToUser(UserDto userDto);

    UserDto mapUserToUserDto(User user);
}
