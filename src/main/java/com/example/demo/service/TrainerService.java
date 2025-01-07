package com.example.demo.service;

import com.example.demo.dao.TrainerDAO;
import com.example.demo.dao.TrainingTypeDAO;
import com.example.demo.dao.UserDAO;
import com.example.demo.dto.AuthDTO;
import com.example.demo.dto.ChangePasswordDTO;
import com.example.demo.dto.TrainerCreateDTO;
import com.example.demo.dto.TrainerUpdateDTO;
import com.example.demo.model.Trainer;
import com.example.demo.model.TrainingType;
import com.example.demo.model.User;
import com.example.demo.utils.PasswordGeneratorUtil;
import com.example.demo.utils.ValidationUtil;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TrainerService {

    private UsernameGeneratorService usernameGeneratorService;
    private static final Logger LOGGER = LoggerFactory.getLogger(TrainerService.class);
    private final TrainerDAO trainerDAO;

    private AuthService authService;
    private UserDAO userDAO;

    private TrainingTypeDAO trainingTypeDAO;

    @Autowired
    public void setUsernameGeneratorService(UsernameGeneratorService usernameGeneratorService) {
        this.usernameGeneratorService = usernameGeneratorService;
    }

    @Autowired
    public void setTrainingTypeDAO(TrainingTypeDAO trainingTypeDAO) {
        this.trainingTypeDAO = trainingTypeDAO;
    }

    @Autowired
    public void setAuthService(AuthService authService) {
        this.authService = authService;
    }

    public TrainerService(TrainerDAO trainerDAO) {
        this.trainerDAO = trainerDAO;
    }

    @Autowired
    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Transactional
    public Optional<Trainer> create(TrainerCreateDTO createDTO) {
        if (createDTO == null) {
            throw new NullPointerException("Trainer create data cannot be null.");
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

        UUID trainingTypeId = createDTO.getTrainingTypeId();
        Optional<TrainingType> optionalTrainingType = trainingTypeDAO
                .findById(trainingTypeId);

        if (optionalTrainingType.isEmpty()) {
            LOGGER.error("No Training Type found with id {}  ", trainingTypeId);
            throw new EntityNotFoundException(
                    "No Training type found with id {} " + trainingTypeId);
        }

        Trainer trainer = new Trainer();
        trainer.setId(UUID.randomUUID());
        trainer.setSpecialization(optionalTrainingType.get());
        trainer.setUser(user);

        Optional<Trainer> optional = trainerDAO.create(trainer);

        if (optional.isEmpty()) {
            LOGGER.error("Failed to create  {} ", createDTO);
            return Optional.empty();
        }

        LOGGER.info("'{}' successfully created", optional.get());

        return optional;
    }

    public List<Trainer> getAll() {
        return trainerDAO.getAll();
    }

    public Optional<Trainer> findById(AuthDTO authDTO, UUID id) {

        Optional<Trainer> existingTrainer = trainerDAO.findById(id);

        if (existingTrainer.isEmpty()) {
            LOGGER.error("No Trainer found with id {} ", id);
            return Optional.empty();
        }

        return existingTrainer;
    }

    public Optional<Trainer> findByUsername(AuthDTO authDTO, String username) {

        authService.authenticate(authDTO);

        Optional<Trainer> optional = trainerDAO.findByUsername(username);

        if (optional.isEmpty()) {
            LOGGER.error("No Trainer found with username {}", username);
            return Optional.empty();
        }

        return optional;

    }

    @Transactional
    public void update(AuthDTO authDTO, TrainerUpdateDTO updateDTO) {
        authService.authenticate(authDTO);

        if (updateDTO == null) {
            throw new NullPointerException("Update data cannot be null.");
        }

        ValidationUtil.validate(updateDTO);

        UUID id = updateDTO.getId();

        Optional<Trainer> optional = findById(authDTO, id);

        if (optional.isEmpty()) {
            LOGGER.error("No Trainer found with id {}", id);
            throw new EntityNotFoundException("No Trainer found with id " + id);
        }

        UUID trainingTypeId = updateDTO.getTrainingTypeId();
        Optional<TrainingType> optionalTrainingType = trainingTypeDAO
                .findById(trainingTypeId);

        if (optionalTrainingType.isEmpty()) {
            LOGGER.error("No Training Type found with id {}  ", trainingTypeId);
            throw new EntityNotFoundException(
                    "No Training type found with id {} " + trainingTypeId);
        }

        Trainer trainer = optional.get();

        User user = trainer.getUser();
        user.setFirstName(updateDTO.getFirstName());
        user.setLastName(updateDTO.getLastName());

        trainer.setSpecialization(optionalTrainingType.get());

        trainerDAO.update(trainer);
    }

    @Transactional
    public void activate(AuthDTO authDTO, UUID id) {
        authService.authenticate(authDTO);

        Optional<Trainer> optional = trainerDAO.findById(id);

        if (optional.isEmpty()) {
            LOGGER.error("No Trainer found with id {}", id);
            throw new EntityNotFoundException("No Trainer found with id " + id);
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

        Optional<Trainer> optional = trainerDAO.findById(id);

        if (optional.isEmpty()) {
            LOGGER.error("No Trainer found with id {}", id);
            throw new EntityNotFoundException("No Trainer found with id " + id);
        }

        User user = optional.get().getUser();
        if (!user.isActive()) {
            LOGGER.error("'{}' already inactive", optional.get());
            return;
        }

        user.setActive(false);
        userDAO.update(user);

    }

    @Transactional
    public void changePassword(AuthDTO authDTO, ChangePasswordDTO changePasswordDTO) {
        UUID id = changePasswordDTO.getId();

        Optional<Trainer> optional = trainerDAO.findById(id);

        if (optional.isEmpty()) {
            LOGGER.error("No Trainer found with id {}", id);
            throw new EntityNotFoundException("No Trainer found with id " + id);
        }

        User user = optional.get().getUser();

        user.setPassword(changePasswordDTO.getNewPassword());

        userDAO.update(user);
    }
}
