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
import java.util.Optional;
import java.util.UUID;

@Service
public class TrainingTypeServiceImpl implements TrainingTypeService {

    private final TrainingTypeDAO trainingTypeDAO;

    private AuthService authService;

    private static final Logger LOGGER = LoggerFactory.getLogger(TrainingTypeServiceImpl.class);

    @Autowired
    public void setAuthService(AuthService authService) {
        this.authService = authService;
    }

    public TrainingTypeServiceImpl(TrainingTypeDAO trainingTypeDAO) {
        this.trainingTypeDAO = trainingTypeDAO;
    }

    @Override
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

        optional.ifPresentOrElse(
                t -> LOGGER.info("'{}' successfully created.", t),
                () -> LOGGER.error("Failed to create training type from DTO: {}", createDTO));

        return optional;
    }

    @Override
    public Optional<TrainingType> findById(UUID id) {

        Optional<TrainingType> existingTrainee = trainingTypeDAO.findById(id);

        if (existingTrainee.isEmpty()) {
            return Optional.empty();
        }

        return existingTrainee;
    }

    @Override
    public Optional<TrainingType> findByUsername(AuthDTO authDTO, String username) {

        authService.authenticate(authDTO);

        Optional<TrainingType> optional = trainingTypeDAO.findByName(username);

        if (optional.isEmpty()) {
            LOGGER.warn("No Training type found with name {}", username);
            return Optional.empty();
        }

        return optional;

    }


}
