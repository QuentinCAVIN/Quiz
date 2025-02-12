package com.ynov.repository;

import com.ynov.dto.UserDto;
import com.ynov.model.User;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Optional;

@ApplicationScoped
public class UserRepository implements PanacheRepository<User> {
    public Optional<User> findByUsername(String userName) {
        return find("username", userName).firstResultOptional();
    }

    public Optional<User> findUserByUID(String uid) {
        return find("uid", uid).firstResultOptional();
    }
}
