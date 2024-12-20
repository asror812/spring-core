package com.example.demo.service;

import com.example.demo.dto.TraineeCreateDTO;
import com.example.demo.dto.TrainerCreateDTO;
import com.example.demo.dto.TrainerUpdateDTO;
import com.example.demo.model.Trainee;
import com.example.demo.model.Trainer;
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
public class TrainerServiceTest {

    private TrainerService trainerService;
    private final Map<UUID, Trainer> trainersMap;

    @Autowired
    public TrainerServiceTest(Map<UUID, Trainer> trainersMap) {
        this.trainersMap = trainersMap;
    }

    @Autowired
    public void setTrainerService(TrainerService trainerService){
        this.trainerService = trainerService;
    }

    @BeforeEach
    public void clearStorage() {
        trainersMap.clear();
    }

    @Test
    public void findAllTest() {
        List<Trainer> all = trainerService.findAll();
        Assertions.assertTrue(all.isEmpty());
    }

    @Test
    public void createTrainerWithNull() {
        Assertions.assertThrows(NullPointerException.class, () -> {
            trainerService.create(null);
        });
    }

    @Test
    public void createTrainerTest() {
        TrainerCreateDTO createDTO = new TrainerCreateDTO("A" , "R" , "M");
        trainerService.create(createDTO);

        Collection<Trainer> all = trainersMap.values();

        Assertions.assertEquals(1 , all.size());
        Assertions.assertTrue(all.stream().anyMatch(f -> f.getUsername().equals("A.R")));
    }

    @Test
    public void createTrainerWithExistingUsername(){
        TrainerCreateDTO createDTO1 = new TrainerCreateDTO("Asror", "R", "L");
        Trainer trainee1 = trainerService.create(createDTO1);;

        TrainerCreateDTO createDTO2 = new TrainerCreateDTO("Asror", "R",  "K");
        Trainer trainee2 = trainerService.create(createDTO2);;

        TrainerCreateDTO createDTO3 = new TrainerCreateDTO("Asror", "R",  "S");
        Trainer trainee3 = trainerService.create(createDTO3);;

        Assertions.assertEquals("Asror.R", trainee1.getUsername());
        Assertions.assertEquals("Asror.R1", trainee2.getUsername());
        Assertions.assertEquals("Asror.R2", trainee3.getUsername());
        
    }


    @Test
    public void updateTrainerWithNullId() {
        TrainerUpdateDTO updateDTO = new TrainerUpdateDTO();
        Assertions.assertThrows(NullPointerException.class, () -> {
            trainerService.update(null, updateDTO);
        });
    }
 
    @Test
    public void updateTrainerWithNullDto() {
        TrainerUpdateDTO updateDTO = new TrainerUpdateDTO();
        Assertions.assertThrows(NullPointerException.class, () -> {
        trainerService.update(null, updateDTO);
        });
    }


    @Test
    public void updateTrainerWithNonExistingId() {
        UUID trainerId = UUID.randomUUID();
        TrainerUpdateDTO updateDTO = new TrainerUpdateDTO();

        Assertions.assertThrows(IllegalArgumentException.class , () -> trainerService.update(trainerId, updateDTO));
    }

    @Test
    public void updateTrainerTest() {
        UUID trainerId = UUID.randomUUID();
        Trainer trainer = new Trainer
                (trainerId, "A", "R", "A.A" ,
                "12345678"  , false , "L" );

        trainersMap.put(trainerId, trainer);
        TrainerUpdateDTO updateDTO = new TrainerUpdateDTO("Abror" , "Ruzimurodov" , "asdfg54321", "K" );

        trainerService.update(trainerId, updateDTO);
        Trainer updatedTrainer = trainerService
                                .findById(trainerId)
                                .orElseThrow(
                                    () -> new IllegalArgumentException("Trainee with id " + trainerId + " not found"));

        Assertions.assertEquals(false, updatedTrainer.isActive());
        Assertions.assertEquals("Abror", updatedTrainer.getFirstName());
        Assertions.assertEquals("Ruzimurodov", updatedTrainer.getLastName());
        Assertions.assertEquals("asdfg54321", updatedTrainer.getPassword());
        Assertions.assertEquals("K", updatedTrainer.getSpecialization());
        Assertions.assertEquals("A.A", updatedTrainer.getUsername());
    }
}
