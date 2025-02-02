package com.example.demo.dao;

import java.util.Optional;

import org.springframework.stereotype.Repository;
import com.example.demo.model.User;

@Repository
public interface UserDAO {

    Optional<User> findByUsername(String username);

    void update(User user);

    Optional<User> create(User user);
}
