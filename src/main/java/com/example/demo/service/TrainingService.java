package com.example.demo.service;

import com.example.demo.dao.TraineeDAO;
import com.example.demo.dao.TrainerDAO;
import com.example.demo.dao.TrainingDAO;
import com.example.demo.dto.request.TrainingCreateRequestDTO;
import com.example.demo.dto.request.TrainingUpdateRequestDTO;
import com.example.demo.dto.response.TraineeTrainingResponseDTO;
import com.example.demo.dto.response.TrainerTrainingResponseDTO;
import com.example.demo.dto.response.TrainingResponseDTO;
import com.example.demo.dto.response.TrainingUpdateResponseDTO;
import com.example.demo.mapper.TrainingMapper;
import com.example.demo.model.Trainee;
import com.example.demo.model.Trainer;
import com.example.demo.model.Training;
import com.example.demo.exceptions.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
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

    @Transactional
    public void create(TrainingCreateRequestDTO createDTO) {
        Trainee trainee = traineeDAO.findByUsername(createDTO.getTraineeUsername())
                .orElseThrow(ResourceNotFoundException::new);
        Trainer trainer = trainerDAO.findByUsername(createDTO.getTrainerUsername())
                .orElseThrow(ResourceNotFoundException::new);

        Training training = new Training();
        training.setTrainee(trainee);
        training.setTrainer(trainer);
        training.setTrainingDate(createDTO.getTrainingDate());
        training.setDuration(createDTO.getDuration());
        training.setTrainingType(trainer.getSpecialization());
        training.setTrainingName(createDTO.getTrainingName());

        dao.create(training);

        trainee.getTrainers().add(trainer);
        trainer.getTrainees().add(trainee);

        traineeDAO.update(trainee);
        trainerDAO.update(trainer);
    }

    public List<TrainingResponseDTO> getTraineeTrainings(String username, Date from, Date to, String trainerName,
            String trainingType) {

        return dao.findTraineeTrainings(username, from, to, trainerName, trainingType)
                .stream()
                .map(mapper::toResponseDTO)
                .toList();
    }

    public List<TrainingResponseDTO> getTrainerTrainings(String username, Date from, Date to, String traineeName) {
        return dao.findTrainerTrainings(username, from, to, traineeName)
                .stream()
                .map(mapper::toResponseDTO)
                .toList();
    }

    @Override
    protected TrainingUpdateResponseDTO internalUpdate(TrainingUpdateRequestDTO updateDTO) {
        // TODO: Auto-generated method
        throw new UnsupportedOperationException("Unimplemented method 'internalUpdate'");
    }

    public List<TrainerTrainingResponseDTO> getTrainerTrainings(String username) {
        Trainer trainer = trainerDAO.findByUsername(username)
                .orElseThrow(ResourceNotFoundException::new);
        return trainer.getTrainings().stream().map(mapper::toTrainerTrainingResponseDTO).toList();
    }

    public List<TraineeTrainingResponseDTO> getTraineeTrainings(String username) {
        Trainee trainee = traineeDAO.findByUsername(username)
                .orElseThrow(ResourceNotFoundException::new);
        return trainee.getTrainings().stream().map(mapper::toTraineeTrainingResponseDTO).toList();
    }

}
