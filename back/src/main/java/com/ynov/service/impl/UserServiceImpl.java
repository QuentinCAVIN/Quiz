package com.ynov.service.impl;

import com.ynov.component.UserMapper;
import com.ynov.dto.UserDto;
import com.ynov.model.User;
import com.ynov.repository.UserRepository;
import com.ynov.service.IUserService;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements IUserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public Optional<UserDto> findUserByID(long id) {
        return Optional.empty();
    }

    @Override
    public Optional<UserDto> findUserByUsername(String name) {
        return Optional.empty();
    }

    @Override
    public List<UserDto> findAllUser() {
        return List.of();
    }

    @Override
    public void createUser(UserDto userDTO, String uid) {
        User user = userMapper.mapUserDtoToUser(userDTO);
        user.setUid(uid);
        log.info("User {} created :).", user.getUsername());
        userRepository.persist(user);
    }

    @Override
    public void deleteUser(long id) {

    }

    @Override
    public Optional<Long> checkAuthUser(String name, String password) {
        return Optional.empty();
    }

    @Override
    public void createAdmin(String username, String password) {

    }
}
