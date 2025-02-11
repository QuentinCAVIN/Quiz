package com.ynov.component;

import com.ynov.dto.UserDto;
import com.ynov.model.User;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UserMapper {
    public User mapUserDtoToUser (UserDto userDto) {
        return User.builder()
                .email(userDto.getEmail())
                .username(userDto.getUsername())
                .build();
    }

    public UserDto mapUserToUserDto (User user) {
        return UserDto.builder()
                .email(user.getEmail())
                .username(user.getUsername())
                .build();
    }
}
