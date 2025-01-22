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
import jakarta.validation.ValidationException;

import com.example.demo.dto.AuthDTO;
import com.example.demo.dto.ChangePasswordDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.UUID;

@Service
public class TraineeServiceImpl implements TraineeService{

    private UserDAO userDAO;
    private AuthService authService;
    private UsernameGeneratorService usernameGeneratorService;

    private final TraineeDAO traineeDAO;
    private static final String NO_TRAINEE_FOUND_STR_FOR_LOGGER = "No Trainee found with id {}"; 
    private static final String NO_TRAINEE_FOUND_STR = "No Trainee found with id ";
    private static final Logger LOGGER = LoggerFactory.getLogger(TraineeServiceImpl.class);

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

    public TraineeServiceImpl(TraineeDAO traineeDAO) {
        this.traineeDAO = traineeDAO;
    }

    @Override
    @Transactional
    public Optional<Trainee> create(TraineeCreateDTO createDTO) {

        if (createDTO == null) {
            throw new ValidationException("Trainee create data cannot be null.");
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
        
        userDAO.create(user);
        
        Trainee trainee = new Trainee();
        trainee.setId(UUID.randomUUID());
        trainee.setAddress(createDTO.getAddress());
        trainee.setDateOfBirth(createDTO.getDateOfBirth());
        trainee.setUser(user);

        Optional<Trainee> optional = traineeDAO.create(trainee);

        optional.ifPresentOrElse(
                t -> LOGGER.info("'{}' successfully created.", t),
                () -> LOGGER.error("Failed to create trainee from DTO: {}", createDTO));


        return optional;
    }

    @Override
    public Optional<Trainee> findByUsername(AuthDTO authDTO, String username) {

        authService.authenticate(authDTO);

        Optional<Trainee> optional = traineeDAO.findByUsername(username);

        if (optional.isEmpty()) {
            LOGGER.warn("No Trainee found with username {}", username);
            return Optional.empty();
        }

        return optional;
    }

    @Override
    @Transactional
    public void update(AuthDTO authDTO, TraineeUpdateDTO updateDTO) {

        authService.authenticate(authDTO);

        if (updateDTO == null) {
            throw new ValidationException("Trainee update data cannot be null.");
        }

        ValidationUtil.validate(authDTO);

        UUID id = updateDTO.getId();

        Optional<Trainee> optional = traineeDAO.findById(id);

        if (optional.isEmpty()) {
            LOGGER.error(NO_TRAINEE_FOUND_STR_FOR_LOGGER, id);
            throw new EntityNotFoundException(NO_TRAINEE_FOUND_STR + id);
        }

        User user = optional.get().getUser();

        user.setFirstName(updateDTO.getFirstName());
        user.setLastName(updateDTO.getLastName());

        userDAO.update(user);

        Trainee trainee = optional.get();
        trainee.setAddress(updateDTO.getAddress());
        trainee.setDateOfBirth(updateDTO.getDateOfBirth());

        traineeDAO.update(trainee);
    }

    @Override
    @Transactional
    public void delete(AuthDTO authDTO, UUID id) {

        authService.authenticate(authDTO);

        Optional<Trainee> optional = traineeDAO.findById(id);

        if (optional.isEmpty()) {
            LOGGER.error(NO_TRAINEE_FOUND_STR_FOR_LOGGER, id);
            return;
        }

        traineeDAO.delete(optional.get());
    }

    @Override
    @Transactional
    public void activate(AuthDTO authDTO, UUID id) {
        authService.authenticate(authDTO);

        Optional<Trainee> optional = traineeDAO.findById(id);

        if (optional.isEmpty()) {
            LOGGER.error(NO_TRAINEE_FOUND_STR_FOR_LOGGER, id);
            throw new EntityNotFoundException(NO_TRAINEE_FOUND_STR + id);
        }

        User user = optional.get().getUser();
        if (user.isActive()) {
            LOGGER.error("'{}' already active", optional.get());
            return;
        }

        user.setActive(true);
        userDAO.update(user);
    }

    @Override
    @Transactional
    public void deactivate(AuthDTO authDTO, UUID id) {
        authService.authenticate(authDTO);

        Optional<Trainee> optional = traineeDAO.findById(id);

        if (optional.isEmpty()) {
            LOGGER.error(NO_TRAINEE_FOUND_STR_FOR_LOGGER, id);
            throw new EntityNotFoundException(NO_TRAINEE_FOUND_STR + id);
        }

        User user = optional.get().getUser();
        if (!user.isActive()) {
            LOGGER.error("'{}'' already inactive", optional.get());
            return;
        }

        user.setActive(false);
        userDAO.update(user);

    }

    @Override
    @Transactional
    public void changePassword(AuthDTO authDTO, ChangePasswordDTO changePasswordDTO) {

        authService.authenticate(authDTO);

        UUID id = changePasswordDTO.getId();

        Optional<Trainee> optional = traineeDAO.findById(id);

        if (optional.isEmpty()) {
            LOGGER.error(NO_TRAINEE_FOUND_STR_FOR_LOGGER, id);
            throw new EntityNotFoundException(NO_TRAINEE_FOUND_STR + id);
        }

        User user = optional.get().getUser();

        user.setPassword(changePasswordDTO.getNewPassword());

        userDAO.update(user);
    }

}
