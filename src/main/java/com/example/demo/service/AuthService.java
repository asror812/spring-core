package com.example.demo.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.example.demo.dao.UserDAO;
import com.example.demo.dto.AuthDTO;
import com.example.demo.model.User;

@Service
public class AuthService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthService.class);

    private final UserDAO userDAO;

    public AuthService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public void authenticate(AuthDTO authDTO) {

        String password = authDTO.getPassword();
        String username = authDTO.getUsername();

        Optional<User> existingUser = userDAO.findByUsername(username);
    
        if (existingUser.isEmpty()) {
            LOGGER.error("Authentication failed for username: {}", username);
            throw new SecurityException("Invalid1 username or password");
        }

        User user = existingUser.get();

        if (!(user.getUsername().equals(username) && user.getPassword().equals(password))) {
            LOGGER.error("Authentication failed for username: {}", username);
            throw new SecurityException("Invalid2 username or password");
        }

        LOGGER.info("User '{}' authenticated successfully", username);
    }

}
