package com.example.demo.service;

import com.example.demo.dto.TraineeCreateDTO;
import com.example.demo.dto.TrainerCreateDTO;
import com.example.demo.dto.TrainingCreateDTO;
import com.example.demo.model.Trainee;
import com.example.demo.model.Trainer;
import com.example.demo.model.Training;
import com.example.demo.utils.PasswordGenerator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;

@SpringBootTest
public class TrainingServiceTest {

    private TrainingService trainingService;
    private TrainerService trainerService;
    private TraineeService traineeService;



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
        TraineeCreateDTO traineeCreateDTO = new TraineeCreateDTO("Asror", "R", "Tashkent" , LocalDate.of(2004 , 8 , 12));

        Trainee trainee = traineeService.create(traineeCreateDTO);; 
        
        TrainerCreateDTO trainerCreateDTO = new TrainerCreateDTO("A" , "R" , "M");
        Trainer trainer = trainerService.create(trainerCreateDTO);;

        TrainingCreateDTO createDTO = new TrainingCreateDTO(trainee.getUserId(), trainer.getUserId(),
         "L",  "L", LocalDate.now(), 1.5);

         trainingService.create(createDTO);;

        Assertions.assertEquals(1, trainingsMap.size());
        Assertions.assertTrue(trainingsMap.values().stream().anyMatch(b -> b.getTrainingName().equals("L")));


    }

    public void createTrainingTestWithNull() {
     
        Assertions.assertThrows(IllegalArgumentException.class, () ->
            trainingService.create(null)); 
    }

}
