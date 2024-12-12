package com.example.demo.service;


import com.example.demo.dto.TraineeCreateDTO;
import com.example.demo.dto.TraineeUpdateDTO;
import com.example.demo.model.Trainee;
import com.example.demo.storage.PasswordGenerator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@SpringBootTest
public class TraineeServiceTest {

    private  TraineeService traineeService;

    private Map<UUID , Trainee> traineesMap;

    @Autowired
    public TraineeServiceTest(Map<UUID , Trainee> traineesMap) {
        this.traineesMap = traineesMap;
    }

    private PasswordGenerator passwordGenerator;

    @BeforeEach
    public void setUp() {
        traineesMap.clear();
    }

    @Test
    public void createTraineeTest() {
       TraineeCreateDTO createDTO = new TraineeCreateDTO();

        createDTO.setFirstName("Asror");
        createDTO.setLastName("R");
        createDTO.setDateOfBirth(LocalDate.of(2004 , 8 , 12));
        createDTO.setAddress("Tashkent");

        traineeService.create(createDTO);

       Assertions.assertEquals(1 , traineesMap.size());
       Assertions.assertTrue(traineesMap.values().stream().anyMatch(f -> f.getUsername().equals("Asror.R")));
    }

    @Test
    public void updateTraineeTest() {
        UUID traineeId = UUID.randomUUID();
        Trainee trainee = new Trainee(traineeId, "Asror", "R", "Asror.R" , passwordGenerator.generate(),
                false ,  LocalDate.of(2004, 8, 12), "Tashkent");

        traineesMap.put(trainee.getUserId(), trainee);


        TraineeUpdateDTO updateDTO = new TraineeUpdateDTO();
        updateDTO.setFirstName("Asror");
        updateDTO.setLastName("Ruzimurodov");
        updateDTO.setDateOfBirth(LocalDate.of(2004 , 8 , 12));
        updateDTO.setAddress("Tashkent");
        updateDTO.setPassword("zxcvb12345");


        traineeService.update(traineeId, updateDTO);

        Assertions.assertEquals("Asror.R", traineesMap.get(trainee.getUserId()).getUsername());
        Assertions.assertEquals("zxcvb12345", traineesMap.get(trainee.getUserId()).getPassword());
    }


    @Autowired
    public void setPasswordGenerator(PasswordGenerator passwordGenerator) {
        this.passwordGenerator = passwordGenerator;
    }

    @Autowired
    public void setTraineeService(TraineeService traineeService) {
        this.traineeService = traineeService;
    }

}
