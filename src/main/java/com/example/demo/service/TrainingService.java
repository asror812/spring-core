package com.example.demo.service;

import com.example.demo.dao.TraineeDAO;
import com.example.demo.dao.TrainerDAO;
import com.example.demo.dao.TrainingDAO;
import com.example.demo.dao.TrainingTypeDAO;
import com.example.demo.dto.AuthDTO;
import com.example.demo.dto.TraineeCriteriaDTO;
import com.example.demo.dto.TrainerCriteriaDTO;
import com.example.demo.dto.TrainingCreateDTO;
import com.example.demo.model.Trainee;
import com.example.demo.model.Trainer;
import com.example.demo.model.Training;
import com.example.demo.model.TrainingType;
import com.example.demo.utils.ValidationUtil;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class TrainingService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TrainingService.class);
    private TrainerDAO trainerDAO;
    private TraineeDAO traineeDAO;
    private AuthService authService;
    private TrainingTypeDAO trainingTypeDAO;

    private final TrainingDAO trainingDAO;

    @Autowired
    public void setTrainingTypeDAO(TrainingTypeDAO trainingTypeDAO) {
        this.trainingTypeDAO = trainingTypeDAO;
    }

    @Autowired
    public void setTrainerDAO(TrainerDAO trainerDAO) {
        this.trainerDAO = trainerDAO;
    }

    @Autowired
    public void setAuthService(AuthService authService) {
        this.authService = authService;
    }

    @Autowired
    public void setTraineeDAO(TraineeDAO traineeDAO) {
        this.traineeDAO = traineeDAO;
    }

    public TrainingService(TrainingDAO trainingDAO) {
        this.trainingDAO = trainingDAO;
    }

    @Transactional
    public Optional<Training> create(AuthDTO authDTO, TrainingCreateDTO createDTO) {

        authService.authenticate(authDTO);

        if (createDTO == null) {
            throw new NullPointerException("Training create data cannot be null.");
        }

        ValidationUtil.validate(createDTO);

        UUID traineeId = createDTO.getTraineeId();
        UUID trainerId = createDTO.getTrainerId();

        Optional<Trainee> optionalTrainee = traineeDAO.findById(traineeId);

        Optional<Trainer> optionalTrainer = trainerDAO.findById(trainerId);

        if (optionalTrainee.isEmpty() || optionalTrainer.isEmpty()) {
            throw new EntityNotFoundException("Trainee or trainer not found");
        }

        Trainee trainee = optionalTrainee.get();
        Trainer trainer = optionalTrainer.get();

        UUID trainingTypeId = createDTO.getTrainingTypeId();
        Optional<TrainingType> optionalTrainingType = trainingTypeDAO.findById(trainingTypeId);

        if (optionalTrainingType.isEmpty()) {
            LOGGER.error("No Training Type found with id {}  ", trainingTypeId);
            throw new IllegalArgumentException(
                    "No Trainee found with id {} " + trainingTypeId);
        }

        TrainingType trainingType = optionalTrainingType.get();

        Training training = new Training();
        training.setId(UUID.randomUUID());
        training.setTrainee(trainee);
        training.setTrainer(trainer);
        training.setTrainingDate(createDTO.getTrainingDate());
        training.setDuration(createDTO.getDuration());
        training.setTrainingName(trainingType.getTrainingTypeName());
        training.setTrainingType(trainingType);

        Optional<Training> optional = trainingDAO.create(training);

        if (optional.isEmpty()) {
            LOGGER.error("Failed to create  {} ", createDTO);
            return Optional.empty();
        }

        LOGGER.info("{} successfully created", optional.get());

        return optional;

    }

    public List<Training> getAll() {
        return trainingDAO.getAll();
    }

    public List<Training> getTraineeTrainings(AuthDTO authDTO, TraineeCriteriaDTO criteriaDTO) {
        authService.authenticate(authDTO);

        if (criteriaDTO == null) {
            throw new NullPointerException("Training Trainee Criteria data cannot be null.");
        }

        ValidationUtil.validate(criteriaDTO);

        List<Training> traineeTrainings = trainingDAO.findTraineeTrainings(criteriaDTO.getUsername(),
                criteriaDTO.getFrom(), criteriaDTO.getTo(),
                criteriaDTO.getTrainerName(), criteriaDTO.getTrainingType());

        return traineeTrainings;
    }

    public List<Training> getTrainerTrainings(AuthDTO authDTO, TrainerCriteriaDTO criteriaDTO) {
        authService.authenticate(authDTO);

        if (criteriaDTO == null) {
            throw new NullPointerException("Training Trainer Criteria data cannot be null.");
        }

        ValidationUtil.validate(criteriaDTO);

        List<Training> trainerTrainings = trainingDAO.findTrainerTrainings(criteriaDTO.getUsername(),
                criteriaDTO.getFrom(), criteriaDTO.getTo(), criteriaDTO.getTraineeName());

        return trainerTrainings;
    }

    public List<Trainer> getNotAssignedTrainers(AuthDTO authDTO, String traineeUsername) {
        authService.authenticate(authDTO);

        if (traineeUsername == null || traineeUsername.trim().isEmpty()) {
            throw new NullPointerException("Trainee username cannot be null.");
        }

        Optional<Trainee> optional = traineeDAO.findByUsername(traineeUsername);

        if (optional.isEmpty()) {
            LOGGER.error("No Trainee found with username {} ", traineeUsername);
            throw new EntityNotFoundException(
                    "No Trainee found with username " + traineeUsername);
        }

        List<Training> traineeTrainings = trainingDAO.findTraineeTrainingsById(optional.get().getId());
        List<Trainer> all = trainerDAO.getAll();

        Set<UUID> traineeTrainingIds = traineeTrainings
                .stream()
                .map(tr -> tr.getTrainer().getId())
                .collect(Collectors.toSet());
                
        all.removeIf(trainer -> traineeTrainingIds.contains(trainer.getId()));

        return all;
    }

}
