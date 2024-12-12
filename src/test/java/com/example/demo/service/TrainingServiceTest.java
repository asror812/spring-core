package com.example.demo.service;

import com.example.demo.dto.TraineeCreateDTO;
import com.example.demo.model.Training;
import com.example.demo.storage.PasswordGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@SpringBootTest
public class TrainingServiceTest {

    private TrainingService trainingService;
    private PasswordGenerator passwordGenerator;


    private  Map<UUID , Training> trainingsMap;

    @Autowired
    public TrainingServiceTest(TrainingService trainingService) {
        this.trainingService = trainingService;
    }

    @BeforeEach
    public void setUp() {
        trainingsMap  = new HashMap<>();
    }


    public void createTrainingTest(TraineeCreateDTO createDTO) {


    }

    @Autowired
    public void setTrainingsMap(Map<UUID , Training> trainingsMap) {
        this.trainingsMap = trainingsMap;
    }

    @Autowired
    public void setPasswordGenerator(PasswordGenerator passwordGenerator) {
        this.passwordGenerator = passwordGenerator;
    }
}
