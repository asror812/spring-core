package com.example.demo.service;

import com.example.demo.dto.TrainerCreateDTO;
import com.example.demo.dto.TrainerUpdateDTO;
import com.example.demo.model.Trainer;
import com.example.demo.utils.PasswordGenerator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.Map;
import java.util.UUID;

@SpringBootTest
public class TrainerServiceTest {



    private  TrainerService trainerService;



    private PasswordGenerator passwordGenerator;


    private final Map<UUID, Trainer> trainersMap;


    @Autowired
    public TrainerServiceTest(Map<UUID , Trainer> trainersMap) {
        this.trainersMap = trainersMap;
    }

    @Autowired
    public void setTrainerService(TrainerService trainerService) {
        this.trainerService = trainerService;
    }

    @BeforeEach
    public void setUp() {
        trainersMap.clear();
    }



    @Test
    public void createTrainerTest() {
        TrainerCreateDTO createDTO = new TrainerCreateDTO("A" , "R" , "M");
        trainerService.create(createDTO);

        Assertions.assertEquals(1 , trainersMap.size());
        Assertions.assertTrue(trainersMap.values().stream().anyMatch(f -> f.getUsername().equals("A.R")));


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



        Assertions.assertEquals(false, trainersMap.get(trainer.getUserId()).isActive());
        Assertions.assertEquals("Abror", trainersMap.get(trainer.getUserId()).getFirstName());
        Assertions.assertEquals("Ruzimurodov", trainersMap.get(trainer.getUserId()).getLastName());
        Assertions.assertEquals("asdfg54321", trainersMap.get(trainer.getUserId()).getPassword());
        Assertions.assertEquals("K", trainersMap.get(trainer.getUserId()).getSpecialization());


        Assertions.assertEquals("A.A", trainersMap.get(trainer.getUserId()).getUsername());
     
    }


    @Autowired
    public void setPasswordGenerator(PasswordGenerator passwordGenerator) {
        this.passwordGenerator = passwordGenerator;
    }

}
