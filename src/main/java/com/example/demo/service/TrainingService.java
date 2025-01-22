package com.example.demo.service;


import com.example.demo.dto.AuthDTO;
import com.example.demo.dto.TraineeCriteriaDTO;
import com.example.demo.dto.TrainerCriteriaDTO;
import com.example.demo.dto.TrainingCreateDTO;
import com.example.demo.model.Trainer;
import com.example.demo.model.Training;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface TrainingService {

    Optional<Training> create(AuthDTO authDTO, TrainingCreateDTO createDTO);

    List<Training> getTraineeTrainings(AuthDTO authDTO, TraineeCriteriaDTO criteriaDTO);

    List<Training> getTrainerTrainings(AuthDTO authDTO, TrainerCriteriaDTO criteriaDTO);

    List<Trainer> getNotAssignedTrainers(AuthDTO authDTO, String traineeUsername);
}