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



    @BeforeEach
    public void setUp() {
        trainingsMap.clear();
    }


    public void createTrainingTest(TraineeCreateDTO createDTO) {


    }

}
