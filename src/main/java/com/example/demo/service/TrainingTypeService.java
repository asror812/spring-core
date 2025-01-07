package com.example.demo.service;

import com.example.demo.dao.TrainingTypeDAO;
import com.example.demo.dto.TrainingTypeCreateDTO;
import com.example.demo.model.TrainingType;
import com.example.demo.utils.ValidationUtil;
import jakarta.transaction.Transactional;
import com.example.demo.dto.AuthDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TrainingTypeService {

    private final TrainingTypeDAO trainingTypeDAO;

    private AuthService authService;

    private static final Logger LOGGER = LoggerFactory.getLogger(TraineeService.class);

    @Autowired
    public void setAuthService(AuthService authService) {
        this.authService = authService;
    }

    public TrainingTypeService(TrainingTypeDAO trainingTypeDAO) {
        this.trainingTypeDAO = trainingTypeDAO;
    }

    @Transactional
    public Optional<TrainingType> create(AuthDTO authDTO, TrainingTypeCreateDTO createDTO) {

        authService.authenticate(authDTO);

        if (createDTO == null) {
            throw new NullPointerException("Trainee create data cannot be null.");
        }

        ValidationUtil.validate(createDTO);

        TrainingType trainingType = new TrainingType();
        trainingType.setId(UUID.randomUUID());
        trainingType.setTrainingTypeName(createDTO.getTrainingTypeName());

        Optional<TrainingType> optional = trainingTypeDAO.create(trainingType);

        if (optional.isEmpty()) {
            LOGGER.error("Failed to create  {} ", createDTO);
            return Optional.empty();
        }

        LOGGER.info("'{}' successfully created.", optional.get());

        return optional;
    }

    public Optional<TrainingType> findById(UUID id) {

        Optional<TrainingType> existingTrainee = trainingTypeDAO.findById(id);

        if (existingTrainee.isEmpty()) {
            return Optional.empty();
        }

        return existingTrainee;
    }

    public List<TrainingType> getAll() {
        return trainingTypeDAO.getAll();
    }

    public Optional<TrainingType> findByUsername(AuthDTO authDTO, String username) {

        authService.authenticate(authDTO);

        Optional<TrainingType> optional = trainingTypeDAO.findByName(username);

        if (optional.isEmpty()) {
            LOGGER.error("No Training type found with name {}", username);
            return Optional.empty();
        }

        return optional;

    }


}
