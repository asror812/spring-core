package com.example.demo.dao;

import com.example.demo.model.Training;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.*;

@Repository
public interface TrainingDAO {
    Optional<Training> create(Training training);

    List<Training> findTraineeTrainings(String username, LocalDate from, LocalDate to, String trainerName,
            String trainingType);

    List<Training> findTrainerTrainings(String username, LocalDate from, LocalDate to, String traineeName);

    List<Training> getAll();

    List<Training> findTraineeTrainingsById(UUID id);
}
