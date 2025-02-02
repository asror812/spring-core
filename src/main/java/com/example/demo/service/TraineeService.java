package com.example.demo.service;

import com.example.demo.dao.TraineeDAO;
import com.example.demo.dao.UserDAO;
import com.example.demo.dto.TraineeCreateDTO;
import com.example.demo.dto.TraineeUpdateDTO;
import com.example.demo.model.Trainee;
import com.example.demo.model.User;
import com.example.demo.utils.PasswordGeneratorUtil;
import com.example.demo.utils.ValidationUtil;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import com.example.demo.dto.AuthDTO;
import com.example.demo.dto.ChangePasswordDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TraineeService {

    private final TraineeDAO traineeDAO;
    private UserDAO userDAO;

    private UsernameGeneratorService usernameGeneratorService;
    private AuthService authService;

    private static final Logger LOGGER = LoggerFactory.getLogger(TraineeService.class);

    @Autowired
    public void setUsernameGeneratorService(UsernameGeneratorService usernameGeneratorService) {
        this.usernameGeneratorService = usernameGeneratorService;
    }

    @Autowired
    public void setAuthService(AuthService authService) {
        this.authService = authService;
    }

    @Autowired
    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public TraineeService(TraineeDAO traineeDAO) {
        this.traineeDAO = traineeDAO;
    }

    @Transactional
    public Optional<Trainee> create(TraineeCreateDTO createDTO) {

        if (createDTO == null) {
            throw new NullPointerException("Trainee create data cannot be null.");
        }

        ValidationUtil.validate(createDTO);

        String username = usernameGeneratorService.generateUsername(createDTO);
        String password = PasswordGeneratorUtil.generate();

        
        User user = new User();
        user.setId(UUID.randomUUID());
        user.setFirstName(createDTO.getFirstName());
        user.setLastName(createDTO.getLastName());
        user.setUsername(username);
        user.setPassword(password);
        user.setActive(true); 
        
        Trainee trainee = new Trainee();

        trainee.setId(UUID.randomUUID());
        trainee.setAddress(createDTO.getAddress());
        trainee.setDateOfBirth(createDTO.getDateOfBirth());
        trainee.setUser(user);

        Optional<Trainee> optional = traineeDAO.create(trainee);

        if (optional.isEmpty()) {
            LOGGER.error("Failed to create  {} ", createDTO);
            return Optional.empty();
        }

        LOGGER.info("'{}' successfully created.", optional.get());

        return optional;
    }

    public Optional<Trainee> findById(AuthDTO authDTO, UUID id) {

        Optional<Trainee> existingTrainee = traineeDAO.findById(id);

        if (existingTrainee.isEmpty()) {
            return Optional.empty();
        }

        return existingTrainee;
    }

    public List<Trainee> getAll() {
        return traineeDAO.getAll();
    }

    public Optional<Trainee> findByUsername(AuthDTO authDTO, String username) {

        authService.authenticate(authDTO);

        Optional<Trainee> optional = traineeDAO.findByUsername(username);

        if (optional.isEmpty()) {
            LOGGER.error("No Trainee found with username {}", username);
            return Optional.empty();
        }

        return optional;

    }

    @Transactional
    public void update(AuthDTO authDTO, TraineeUpdateDTO updateDTO) {

        authService.authenticate(authDTO);

        if (updateDTO == null) {
            throw new NullPointerException("Trainee update data cannot be null.");
        }

        ValidationUtil.validate(authDTO);

        UUID id = updateDTO.getId();

        Optional<Trainee> optional = traineeDAO.findById(id);

        if (optional.isEmpty()) {
            LOGGER.error("No Trainee found with id {}", id);
            throw new EntityNotFoundException("No Trainee found with id " + id);
        }

        User user = optional.get().getUser();

        user.setFirstName(updateDTO.getFirstName());
        user.setLastName(updateDTO.getLastName());

        Trainee trainee = optional.get();
        trainee.setAddress(updateDTO.getAddress());
        trainee.setDateOfBirth(updateDTO.getDateOfBirth());

        traineeDAO.update(trainee);
    }

    @Transactional
    public void delete(AuthDTO authDTO, UUID id) {

        authService.authenticate(authDTO);

        Optional<Trainee> optional = traineeDAO.findById(id);

        if (optional.isEmpty()) {
            LOGGER.error("No Trainee found with id {}", id);
            return;
        }

        traineeDAO.delete(optional.get());
    }

    @Transactional
    public void activate(AuthDTO authDTO, UUID id) {
        authService.authenticate(authDTO);

        Optional<Trainee> optional = traineeDAO.findById(id);

        if (optional.isEmpty()) {
            LOGGER.error("No Trainee found with id {}", id);
            throw new EntityNotFoundException("No Trainee found with id " + id);
        }

        User user = optional.get().getUser();
        if (user.isActive()) {
            LOGGER.error("'{}' already active", optional.get());
            return;
        }

        user.setActive(true);
        userDAO.update(user);
    }

    @Transactional
    public void deactivate(AuthDTO authDTO, UUID id) {
        authService.authenticate(authDTO);

        Optional<Trainee> optional = traineeDAO.findById(id);

        if (optional.isEmpty()) {
            LOGGER.error("No Trainee found with id {}", id);
            throw new EntityNotFoundException("No Trainer found with id " + id);
        }

        User user = optional.get().getUser();
        if (!user.isActive()) {
            LOGGER.error("'{}'' already inactive", optional.get());
            return;
        }

        user.setActive(false);
        userDAO.update(user);

    }

    @Transactional
    public void changePassword(AuthDTO authDTO, ChangePasswordDTO changePasswordDTO) {

        authService.authenticate(authDTO);

        UUID id = changePasswordDTO.getId();

        Optional<Trainee> optional = traineeDAO.findById(id);

        if (optional.isEmpty()) {
            LOGGER.error("No Trainee found with id {}", id);
            throw new EntityNotFoundException("No Trainer found with id " + id);
        }

        User user = optional.get().getUser();

        user.setPassword(changePasswordDTO.getNewPassword());

        userDAO.update(user);
    }

}
