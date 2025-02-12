package com.ynov.component;

import com.ynov.dto.UserDto;
import com.ynov.model.User;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UserMapper {
    public static User mapUserDtoToUser (UserDto userDto) {
        return User.builder()
                .uid(userDto.getUid())
                .email(userDto.getEmail())
                .username(userDto.getUsername())
                .build();
    }

    public static UserDto mapUserToUserDto(User user) {
        return UserDto.builder()
                .uid(user.getUid())
                .email(user.getEmail())
                .username(user.getUsername())
                .build();
    }
}
