package com.example.demo.service;
import org.springframework.stereotype.Service;
import com.example.demo.dao.UserDAO;
import com.example.demo.dto.UserCreateDTO;


@Service
public class UsernameGeneratorService {

    private final UserDAO userDAO;

    public UsernameGeneratorService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public String generateUsername(UserCreateDTO createDTO) {
        String firstName = createDTO.getFirstName();
        String lastName = createDTO.getLastName();

        String baseUsername = firstName + "." + lastName;

        String username = baseUsername;

        int serialNumber = 1;

        while (userDAO.findByUsername(username).isPresent()) {
            username = baseUsername + serialNumber;
            serialNumber++;
        }

        return username;
    }

}
