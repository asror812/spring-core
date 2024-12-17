package com.example.demo.service;


import com.example.demo.dao.TraineeDAO;
import com.example.demo.dao.TrainerDAO;
import com.example.demo.dao.TrainingDAO;
import com.example.demo.dto.TrainingCreateDTO;
import com.example.demo.model.Trainee;
import com.example.demo.model.Trainer;
import com.example.demo.model.Training;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TrainingService {

    private final TrainingDAO trainingDAO;
    private final Logger logger = LoggerFactory.getLogger(TrainingService.class);


    private TrainerDAO trainerDAO;
    private TraineeDAO traineeDAO;

    @Autowired
    public TrainingService(TrainingDAO trainingDAO) {
        this.trainingDAO = trainingDAO;
    }

    public List<Training> findAll() {

        List<Training> trainings = trainingDAO.select();

        trainings.forEach(training -> {logger.info(training.toString());});

        return trainings;
    }

    public Training findById(UUID id) {
        Training trainer = trainingDAO.selectById(id);

        if(trainer == null) {
            logger.warn("Trainer with id {} not found", id);
        }

        else logger.info("Trainer with id {} found", id);

        return trainer;
    }

    public Training create(TrainingCreateDTO createDTO) {
         Training newTraining = new Training();


        Trainer trainer = trainerDAO.selectById(createDTO.getTrainerId());
        Trainee trainee = traineeDAO.selectById(createDTO.getTraineeId());

        if(trainer == null || trainee == null) {
            throw new IllegalArgumentException("Trainer or trainee does not exist");
        }



        newTraining.setTraineeId(createDTO.getTraineeId());
        newTraining.setTrainerId(createDTO.getTrainerId());
        newTraining.setTrainingDate(createDTO.getTrainingDate());
        newTraining.setTrainingDuration(createDTO.getTrainingDuration());
        newTraining.setTrainingName(createDTO.getTrainingName());
        newTraining.setTrainingType(createDTO.getTrainingType());


        trainingDAO.create(newTraining);

        logger.info("Training with trainer id {} , trainee id {} , training date {} , training type {} successfully created" ,
                newTraining.getTrainerId() , newTraining.getTraineeId() , newTraining.getTrainingDate(), newTraining.getTrainingType());

        return newTraining;

    }


    @Autowired
    public void setTrainerDAO(TrainerDAO trainerDAO) {
        this.trainerDAO = trainerDAO;
    }

    @Autowired
    public void  setTraineeDAO(TraineeDAO traineeDAO) {
        this.traineeDAO = traineeDAO;
    }




}
