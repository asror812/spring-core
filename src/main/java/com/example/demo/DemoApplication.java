package com.example.demo;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.example.demo.dao.TraineeDAO;
import com.example.demo.dao.TrainerDAO;
import com.example.demo.dto.AuthDTO;
import com.example.demo.dto.ChangePasswordDTO;
import com.example.demo.dto.TraineeCreateDTO;
import com.example.demo.dto.TraineeUpdateDTO;
import com.example.demo.dto.TrainerCreateDTO;
import com.example.demo.dto.TrainingCreateDTO;
import com.example.demo.dto.TrainingTypeCreateDTO;
import com.example.demo.model.Trainee;
import com.example.demo.model.Trainer;
import com.example.demo.model.Training;
import com.example.demo.model.TrainingType;
import com.example.demo.model.User;
import com.example.demo.service.TraineeService;
import com.example.demo.service.TrainerService;
import com.example.demo.service.TrainingService;
import com.example.demo.service.TrainingTypeService;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

    @Autowired
    private TrainingService trainingService;

    @Autowired
    public TraineeService traineeService;

    @Autowired
    public TrainerService trainerService;

    @Autowired
    private TraineeDAO traineeDAO;

    @Autowired
    private TrainerDAO trainerDAO;

    @Autowired
    private TrainingTypeService trainingTypeService;

    private final static org.slf4j.Logger LOGGER = LoggerFactory.getLogger(DemoApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Override
    public void run(String... args) {

        LOGGER.info("CREATE TRAINEE");
        TraineeCreateDTO createDTO = new TraineeCreateDTO();
        createDTO.setFirstName("A");
        createDTO.setLastName("B");
        createDTO.setAddress("T");
        createDTO.setDateOfBirth(LocalDate.now());

        Optional<Trainee> optional = traineeService.create(createDTO);

        // Find by id
        LOGGER.info("FIND BY ID");
        Optional<Trainee> existingTrainee = traineeDAO.findById(optional.get().getId());

        LOGGER.info("{} ", existingTrainee.get());

        // Get all
        LOGGER.info("GET ALL");
        List<Trainee> all = traineeDAO.getAll();
        for (Trainee trainee1 : all) {
            LOGGER.info("{} ", trainee1);
        }

        // Find by username
        LOGGER.info("FIND BY USERNAME");
        Optional<Trainee> optional2 = traineeDAO.findByUsername("A.B");

        if (optional2.isPresent()) {

            User user = optional.get().getUser();

            AuthDTO authDTO = new AuthDTO();
            authDTO.setPassword(user.getPassword());
            authDTO.setUsername(user.getUsername());

            Trainee trainee = optional2.get();

            UUID traineeId = trainee.getId();
            LOGGER.info("FIND BY USERNAME : ");
            LOGGER.info("{} ", trainee);

            // Password change
            LOGGER.info("Current password : {} ", trainee.getUser().getPassword());

            traineeService.changePassword(authDTO, new ChangePasswordDTO(trainee.getId(), "1234567890"));

            Optional<Trainee> byId = traineeDAO.findById(traineeId);
            LOGGER.info("Future password : {} ", byId.get().getUser().getPassword());

            authDTO.setPassword("1234567890");

            // Deactivate
            traineeService.deactivate(authDTO, traineeId);
            Optional<Trainee> byId1 = traineeDAO.findById(traineeId);
            LOGGER.info("Current state : {} ", byId1.get().getUser().isActive());

            // Activate
            traineeService.activate(authDTO, traineeId);
            Optional<Trainee> byId2 = traineeDAO.findById(traineeId);
            LOGGER.info("Future state  : {} ", byId2.get().getUser().isActive());

            // Update
            Trainee trainee2 = byId2.get();
            User user2 = byId2.get().getUser();
            LOGGER.info("Current info {} {} {} {} ", user2.getFirstName(), user2.getLastName(),
                    trainee2.getAddress(), trainee2.getDateOfBirth());

            TraineeUpdateDTO updateDTO = new TraineeUpdateDTO();
            updateDTO.setAddress("L");
            updateDTO.setDateOfBirth(LocalDate.now());
            updateDTO.setFirstName("L");
            updateDTO.setLastName("L");
            updateDTO.setId(traineeId);

            traineeService.update(authDTO, updateDTO);
            Optional<Trainee> byId3 = traineeDAO.findById(traineeId);

            Trainee trainee3 = byId3.get();
            LOGGER.info("Future state {} {} {} {}", trainee3.getUser().getFirstName(), trainee3.getUser().getLastName(),
                    trainee3.getAddress(), trainee3.getDateOfBirth());
            /*
             * // delete LOGGER.info("DELETE TRAINEE"); traineeService.delete("A.B");
             * Optional<Trainee> deletedTrainee =
             * traineeService.findByUsername("A.B");
             * LOGGER.info("Deleted trainee : {} ", deletedTrainee);
             */

            // Create training type
            LOGGER.info("CREATE TRAINING TYPE");
            TrainingTypeCreateDTO createDTO1 = new TrainingTypeCreateDTO("Swimming");
            Optional<TrainingType> trainingType = trainingTypeService.create(authDTO, createDTO1);

            UUID trainingTypeId = trainingType.get().getId();

            // Create trainer
            LOGGER.info("CREATE TRAINER");
            TrainerCreateDTO createDTO2 = new TrainerCreateDTO();

            createDTO2.setFirstName("B");
            createDTO2.setLastName("B");
            createDTO2.setTrainingTypeId(trainingTypeId);

            trainerService.create(createDTO2);

            // Get all
            LOGGER.info("GET ALL ");
            List<Trainer> allTrainers = trainerService.getAll();
            for (Trainer t : allTrainers) {
                LOGGER.info("{} ", t);

            }

            // Find by username
            LOGGER.info("FIND BY USERNAME");
            Optional<Trainer> existingTrainer2 = trainerService.findByUsername(authDTO, "B.B");
            if (existingTrainer2.isPresent()) {

                Trainer trainer = existingTrainer2.get();
                LOGGER.info("{} ", trainer);

                UUID trainerId = trainer.getId();

                // Find by id
                LOGGER.info("FIND BY ID");
                Optional<Trainer> byId31 = trainerDAO.findById(trainerId);
                LOGGER.info("{} ", byId31.get());

                // Password change
                LOGGER.info("Current password : {} ", trainer.getUser().getPassword());

                trainerService.changePassword(authDTO, new ChangePasswordDTO(trainerId, "1234567890"));

                User byId4 = trainerDAO.findById(trainerId).get().getUser();

                LOGGER.info("Future password password : {} ", byId4.getPassword());

                // Deactivate
                trainerService.deactivate(authDTO, trainerId);
                User byId5 = trainerDAO.findById(trainerId).get().getUser();
                LOGGER.info("Current state : {} ", byId5.isActive());

                // Activate
                trainerService.activate(authDTO, trainerId);
                User byId6 = trainerDAO.findById(trainerId).get().getUser();
                LOGGER.info("Future state  : {} ", byId6.isActive());

                // Not Assigned trainers
                LOGGER.info("NOT ASSIGNED TRAINERS  - 1");
                List<Trainer> notAssignedTrainers1 = trainingService.getNotAssignedTrainers(authDTO, "A.B");

                for (Trainer notAssignedTrainee : notAssignedTrainers1) {
                    LOGGER.info("{} ", notAssignedTrainee);
                }

                LOGGER.info("CREATE TRAINING");
                TrainingCreateDTO trainingCreateDTO = new TrainingCreateDTO();

                trainingCreateDTO.setDuration(1.5);
                trainingCreateDTO.setTraineeId(traineeId);
                trainingCreateDTO.setTrainerId(trainerId);
                trainingCreateDTO.setTrainingDate(LocalDate.now());
                trainingCreateDTO.setTrainingTypeId(trainingTypeId);

                trainingService.create(authDTO, trainingCreateDTO);

                // Find all
                List<Training> trainings = trainingService.getAll();

                LOGGER.info("GET ALL ");
                for (Training t : trainings) {
                    LOGGER.info("{} ", t);
                }

                // Not Assigned trainers
                LOGGER.info("NOT ASSIGNED TRAINERS -  2");
                List<Trainer> notAssignedTrainers2 = trainingService.getNotAssignedTrainers(authDTO, "A.B");

                for (Trainer notAssignedTrainee : notAssignedTrainers2) {
                    LOGGER.info("{} ", notAssignedTrainee);
                }
            }

        }
    }
}
