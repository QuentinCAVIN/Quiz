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
    public Optional<UserDto> findUserByUID(String uid, String email) {
        return userRepository.findUserByUID(uid).map(UserMapper::mapUserToUserDto);
    }

    @Override
    public Optional<UserDto> findUserByUsername(String name) {
        return userRepository.findByUsername(name)
                .map(UserMapper::mapUserToUserDto);
    }

    @Override
    public List<UserDto> findAllUser() {
        return userRepository.findAll()
                .stream()
                .map(UserMapper::mapUserToUserDto)
                .collect(Collectors.toList());
    }

    //TODO Pas de vérification d'unicité du mail dans la BDD avant la création
    @Override
    public void createUser(UserDto userDTO) {
        User user = UserMapper.mapUserDtoToUser(userDTO);
        log.info("User {} created :).", user.getUsername());
        userRepository.persist(user);
    }

    @Override
    public boolean deleteUser(long id) {
        return userRepository.deleteById(id);
    }

    @Override
    public Optional<Long> checkAuthUser(String name, String password) {
        return Optional.empty();
    }

    @Override
    public void createAdmin(String username, String password) {

    }
}
