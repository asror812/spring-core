package com.example.demo.service;

import com.example.demo.dto.TraineeCreateDTO;
import com.example.demo.dto.TraineeUpdateDTO;
import com.example.demo.model.Trainee;
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
public class TraineeServiceTest {

    private TraineeService traineeService;
    private Map<UUID, Trainee> traineesMap;

    @Autowired
    public TraineeServiceTest(Map<UUID, Trainee> traineesMap) {
        this.traineesMap = traineesMap;
    }

    @Autowired
    public void setTraineeService(TraineeService traineeService){
        this.traineeService = traineeService;
    }

    @BeforeEach
    public void clearStorage() {
        traineesMap.clear();
    }

    @Test
    public void findAllTest() {
        List<Trainee> all = traineeService.findAll();
        Assertions.assertTrue(all.isEmpty());
    }

    @Test
    public void createTraineeWithNull() {
        Assertions.assertThrows(NullPointerException.class, () -> {
            traineeService.create(null);
        });
    }

    @Test
    public void createTraineeTest() {
       TraineeCreateDTO createDTO = new TraineeCreateDTO("Asror", "R", "Tashkent" , LocalDate.of(2004 , 8 , 12));
       traineeService.create(createDTO);

       Collection<Trainee> all = traineesMap.values();;
       Assertions.assertEquals(1 , all.size());
       Assertions.assertTrue(all.stream().anyMatch(f -> f.getUsername().equals("Asror.R")));
    }

    @Test
    public void createTraineeWithExistingUsername(){
         TraineeCreateDTO createDTO1 = new TraineeCreateDTO("Asror", "R", "Tashkent", LocalDate.of(2004, 8, 12));
        Trainee trainee1 = traineeService.create(createDTO1);;

        TraineeCreateDTO createDTO2 = new TraineeCreateDTO("Asror", "R", "Tashkent", LocalDate.of(2004, 8, 12));
        Trainee trainee2 = traineeService.create(createDTO2);;

        TraineeCreateDTO createDTO3 = new TraineeCreateDTO("Asror", "R", "Tashkent", LocalDate.of(2004, 8, 12));
        Trainee trainee3 = traineeService.create(createDTO3);;

        Assertions.assertEquals("Asror.R", trainee1.getUsername());
        Assertions.assertEquals("Asror.R1", trainee2.getUsername());
        Assertions.assertEquals("Asror.R2", trainee3.getUsername());
        
    }

    @Test
    public void updateTraineeWithNullId() {
        TraineeUpdateDTO updateDTO = new TraineeUpdateDTO();
        Assertions.assertThrows(NullPointerException.class, () -> {
            traineeService.update(null, updateDTO);
        });
    }

    @Test
    public void updateTraineeWithNullDto() {
        TraineeUpdateDTO updateDTO = new TraineeUpdateDTO();
        Assertions.assertThrows(NullPointerException.class, () -> {
        traineeService.update(null, updateDTO);
    });
    }


    @Test
    public void updateTraineeWithNonExistingId() {
        UUID traineeId = UUID.randomUUID();
        TraineeUpdateDTO updateDTO = new TraineeUpdateDTO("B", "b", "1234567890", true, LocalDate.now(), "T");

        Assertions.assertThrows(IllegalArgumentException.class , () -> traineeService.update(traineeId, updateDTO));
    }

    @Test
    public void updateTraineeTest() {
        UUID traineeId = UUID.randomUUID();
        Trainee trainee = new Trainee(traineeId, "Asror", "R", "Asror.R" , 
        "12345678qwertyu", false,  LocalDate.of(2004, 8, 12), "Tashkent");

        traineesMap.put(trainee.getUserId(), trainee);

        TraineeUpdateDTO updateDTO = new TraineeUpdateDTO();
        updateDTO.setFirstName("Asror");
        updateDTO.setLastName("Ruzimurodov");
        updateDTO.setDateOfBirth(LocalDate.of(2004 , 8 , 12));
        updateDTO.setAddress("Tashkent");
        updateDTO.setPassword("zxcvb12345");

        traineeService.update(traineeId, updateDTO);

        Trainee updatedTrainee = traineeService
            .findById(traineeId)
            .orElseThrow(
                () -> new IllegalArgumentException("Trainee with id " + traineeId + " not found"));

        Assertions.assertEquals("Asror.R", updatedTrainee.getUsername());
        Assertions.assertEquals("Ruzimurodov", updatedTrainee.getLastName());
        Assertions.assertEquals("zxcvb12345", updatedTrainee.getPassword());
    }

    @Test
    public void deleteTraineeTest() {
        UUID traineeId = UUID.randomUUID();
        Trainee trainee = new Trainee(traineeId, "John", "Doe", "John.Doe",
                "password", false, LocalDate.of(1990, 1, 1), "New York");
        traineesMap.put(trainee.getUserId(), trainee);

        traineeService.delete(traineeId);
        Assertions.assertTrue(traineeService.findById(traineeId).isEmpty());
    }

}
