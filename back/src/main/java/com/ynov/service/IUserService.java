package com.ynov.service;

import com.ynov.dto.UserDto;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    Optional<UserDto> findUserByID(long id);

    Optional<UserDto> findUserByUsername(String name);

    List<UserDto> findAllUser();

    void createUser(UserDto UserDTO);

    void deleteUser(long id);

    Optional<Long> checkAuthUser(String name, String password);

    void createAdmin(String username, String password);
}
