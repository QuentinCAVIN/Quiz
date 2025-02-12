package com.ynov.service.impl;

import com.ynov.component.UserMapper;
import com.ynov.dto.UserDto;
import com.ynov.model.User;
import com.ynov.repository.UserRepository;
import com.ynov.service.IUserService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UserServiceImpl implements IUserService {
    private final UserRepository userRepository;

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
        return userRepository.findAll().stream()
                .map(UserMapper::mapUserToUserDto)
                .collect(Collectors.toList());
    }

    @Override
    public void createUser(UserDto userDTO) {
        User user = UserMapper.mapUserDtoToUser(userDTO);
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
