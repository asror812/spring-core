package com.example.demo.service;

import com.example.demo.model.Training;
import com.example.demo.storage.PasswordGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.Map;
import java.util.UUID;

@SpringBootTest
public class TrainingServiceTest {

    private TrainingService trainingService;
    private TrainerService trainerService;
    private TraineeService traineeService;


    private PasswordGenerator passwordGenerator;


    private  final Map<UUID , Training> trainingsMap;

    @Autowired
    public TrainingServiceTest(Map<UUID , Training> trainingsMap) {
        this.trainingsMap = trainingsMap;
    }

    @Autowired
    public void setTrainingService(TrainingService trainingService) {
        this.trainingService = trainingService;
    }


    @Autowired
    public void setPasswordGenerator(PasswordGenerator passwordGenerator) {
        this.passwordGenerator = passwordGenerator;
    }

    @Autowired
    public void setTrainingsService(TrainingService trainingService) {
        this.trainingService = trainingService;
    }

    @Autowired
    public void setTraineeService(TraineeService traineeService) {
        this.traineeService = traineeService;
    }

    @Autowired
    public void setTrainerService(TrainerService trainerService) {
        this.trainerService = trainerService;
    }



    @BeforeEach
    public void setUp() {
        trainingsMap.clear();
    }


    public void createTrainingTest() {

    }

}
