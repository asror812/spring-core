package com.example.demo.service;



import com.example.demo.dto.TrainerCreateDTO;
import com.example.demo.dto.TrainerUpdateDTO;
import com.example.demo.model.Trainer;
import com.example.demo.storage.PasswordGenerator;
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
        TrainerCreateDTO createDTO = new TrainerCreateDTO();
        String password = passwordGenerator.generate();

        createDTO.setFirstName("Asror");
        createDTO.setLastName("R");
        createDTO.setSpecialization("M");

        createDTO.setPassword(password);


        trainerService.create(createDTO);

        Assertions.assertEquals(1 , trainersMap.size());
        Assertions.assertTrue(trainersMap.values().stream().anyMatch(f -> f.getUsername().equals("Asror.R")));


    }

    @Test
    public void updateTrainerTest() {
        UUID trainerId = UUID.randomUUID();
        Trainer trainer = new Trainer
                (trainerId, "Asror", "R", "Asror.R" ,
                "G"  , false ,  passwordGenerator.generate()  );


        trainersMap.put(trainerId, trainer);


        TrainerUpdateDTO updateDTO = new TrainerUpdateDTO();
        updateDTO.setFirstName("Asror");
        updateDTO.setLastName("Ruzimurodov");
        updateDTO.setSpecialization("K");
        updateDTO.setPassword("asdfg54321");

        trainerService.update(trainerId, updateDTO);

        Assertions.assertEquals("Asror.R", trainersMap.get(trainer.getUserId()).getUsername());
        Assertions.assertEquals("Ruzimurodov", trainersMap.get(trainer.getUserId()).getLastName());
        Assertions.assertEquals("asdfg54321", trainersMap.get(trainer.getUserId()).getPassword());
    }


    @Autowired
    public void setPasswordGenerator(PasswordGenerator passwordGenerator) {
        this.passwordGenerator = passwordGenerator;
    }

}
