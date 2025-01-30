package com.example.demo.service;

import com.example.demo.dao.TraineeDAO;
import com.example.demo.dao.TrainerDAO;
import com.example.demo.dao.TrainingDAO;
import com.example.demo.dto.TraineeCriteriaDTO;
import com.example.demo.dto.TrainerCriteriaDTO;
import com.example.demo.dto.request.TrainingCreateRequestDTO;
import com.example.demo.dto.request.TrainingUpdateRequestDTO;
import com.example.demo.dto.response.TrainingResponseDTO;
import com.example.demo.mapper.TrainingMapper;
import com.example.demo.model.Trainee;
import com.example.demo.model.Trainer;
import com.example.demo.model.Training;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@Getter
@RequiredArgsConstructor
public class TrainingService extends
        AbstractGenericService<Training, TrainingCreateRequestDTO, TrainingUpdateRequestDTO, TrainingResponseDTO, TrainingUpdateResponseDTO> {

    private final TrainerDAO trainerDAO;
    private final TraineeDAO traineeDAO;

    private final TrainingDAO dao;
    private final TrainingMapper mapper;
    private final Class<Training> entityClass = Training.class;
    private static final String NO_TRAINEE_FOUND_WITH_USERNAME = "No Trainee found with username %s";
    private static final String NO_TRAINER_FOUND_WITH_USERNAME = "No Trainer found with username %s";

    @Transactional
    public void create(TrainingCreateRequestDTO createDTO) {

        String traineeUsername = createDTO.getTraineeUsername();
        String trainerUsername = createDTO.getTrainerUsername();

        Trainee trainee = traineeDAO.findByUsername(traineeUsername)
                .orElseThrow(() -> new EntityNotFoundException(
                        NO_TRAINEE_FOUND_WITH_USERNAME.formatted(createDTO.getTraineeUsername())));

        Trainer trainer = trainerDAO.findByUsername(trainerUsername)
                .orElseThrow(() -> new EntityNotFoundException(
                        NO_TRAINER_FOUND_WITH_USERNAME.formatted(createDTO.getTrainerUsername())));

        Training training = new Training();

        training.setTrainee(trainee);
        training.setTrainer(trainer);
        training.setTrainingDate(createDTO.getTrainingDate());
        training.setDuration(createDTO.getDuration());
        training.setTrainingType(trainer.getSpecialization());
        training.setTrainingName(trainerUsername);

        dao.create(training);

        trainee.getTrainers().add(trainer);
        trainer.getTrainees().add(trainee);

        traineeDAO.update(trainee);
        trainerDAO.update(trainer);

    }

    public List<Training> getTraineeTrainings(TraineeCriteriaDTO criteriaDTO) {

        return dao.findTraineeTrainings(criteriaDTO.getUsername(),
                criteriaDTO.getFrom(), criteriaDTO.getTo(),
                criteriaDTO.getTrainerName(), criteriaDTO.getTrainingType());
    }

    public List<Training> getTrainerTrainings(TrainerCriteriaDTO criteriaDTO) {
        return dao.findTrainerTrainings(criteriaDTO.getUsername(),
                criteriaDTO.getFrom(), criteriaDTO.getTo(), criteriaDTO.getTraineeName());
    }

    @Override
    protected TrainingUpdateResponseDTO internalUpdate(TrainingUpdateRequestDTO updateDTO) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'internalUpdate'");
    }

    public List<TrainingResponseDTO> getTrainerTrainings(String username) {
        Trainer trainer = trainerDAO.findByUsername(username)
                .orElseThrow(
                        () -> new EntityNotFoundException(
                                NO_TRAINER_FOUND_WITH_USERNAME.formatted(username)));

        return trainer.getTrainings().stream().map(mapper::toResponseDTO).toList();
    }

    public List<TrainingResponseDTO> getTraineeTrainings(String username) {
        Trainee trainee = traineeDAO.findByUsername(username)
                .orElseThrow(
                        () -> new EntityNotFoundException(NO_TRAINEE_FOUND_WITH_USERNAME.formatted(username)));

        return trainee.getTrainings().stream().map(mapper::toResponseDTO).toList();
    }
}
