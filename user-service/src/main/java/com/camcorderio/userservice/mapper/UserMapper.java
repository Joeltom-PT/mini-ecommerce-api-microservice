package com.camcorderio.userservice.mapper;

import com.camcorderio.userservice.dto.UserDto;
import com.camcorderio.userservice.model.User;

public class UserMapper {

    public static User mapToUser(UserDto userDto) {
        if (userDto == null) {
            return null;
        }

        User user = new User();
        user.setId(userDto.getId());
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setUserRole(userDto.getUserRole());

        return user;
    }
}
