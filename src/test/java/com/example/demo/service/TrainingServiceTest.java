package com.example.demo.service;

import com.example.demo.dto.TraineeCreateDTO;
import com.example.demo.dto.TrainerCreateDTO;
import com.example.demo.dto.TrainingCreateDTO;
import com.example.demo.model.Trainee;
import com.example.demo.model.Trainer;
import com.example.demo.model.Training;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@SpringBootTest
public class TrainingServiceTest {

    private TrainingService trainingService;
    private TrainerService trainerService;
    private TraineeService traineeService;
    private final Map<UUID, Training> trainingsMap;

    @Autowired
    public TrainingServiceTest(Map<UUID, Training> trainingsMap) {
        this.trainingsMap = trainingsMap;
    }

    @Autowired
    public void setTrainingService(TrainingService trainingService) {
        this.trainingService = trainingService;
    }

    @Autowired
    public void setTrainerService(TrainerService trainerService) {
        this.trainerService = trainerService;
    }

    @Autowired
    public void setTraineeService(TraineeService traineeService) {
        this.traineeService = traineeService;
    }

    @BeforeEach
    public void clearStorage() {
        trainingsMap.clear();
    }

    @Test
    public void findAllTest() {
        List<Trainee> all = traineeService.findAll();
        Assertions.assertTrue(all.isEmpty());
    }

    @Test
    public void createTrainingTest() {
        TraineeCreateDTO traineeCreateDTO = new TraineeCreateDTO("Asror", "R", "Tashkent" , LocalDate.of(2004 , 8 , 12));
        Trainee trainee = traineeService.create(traineeCreateDTO);; 
        
        TrainerCreateDTO trainerCreateDTO = new TrainerCreateDTO("A" , "R" , "M");
        Trainer trainer = trainerService.create(trainerCreateDTO);;

        TrainingCreateDTO createDTO = new TrainingCreateDTO(trainee.getUserId(), trainer.getUserId(),
         "L",  "L", LocalDate.now(), 1.5);
        trainingService.create(createDTO);

        Collection<Training> all = trainingsMap.values();

        Assertions.assertEquals(1, all.size());
        Assertions.assertTrue(all.stream().anyMatch(b -> b.getTrainingName().equals("L")));
    }

    @Test
    public void createTrainingTestWithNull() {
        Assertions.assertThrows(NullPointerException.class, () ->
                                            trainingService.create(null)); 
    }

    @Test
    public void createTrainingWithNonExistingTrainerAndTrainee(){

        TrainingCreateDTO createDTO = new TrainingCreateDTO(UUID.randomUUID(), UUID.randomUUID(),
                                                "L", "L", LocalDate.now(), 1.5);

        Assertions.assertThrows(IllegalArgumentException.class, () -> 
                                            trainingService.create(createDTO));        

    }
}
