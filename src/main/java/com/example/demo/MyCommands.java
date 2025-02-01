package com.example.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.demo.dao.TraineeDAO;
import com.example.demo.dao.TrainerDAO;
import com.example.demo.service.TraineeService;
import com.example.demo.service.TrainerService;
import com.example.demo.service.TrainingService;
import com.example.demo.service.TrainingTypeService;

/* 
@Component
public class MyCommands implements CommandLineRunner {


     private TrainingService trainingService;

    private TraineeDAO traineeDAO;

    private TrainerDAO trainerDAO;

    private TrainingTypeService trainingTypeService;

    private TraineeService traineeService;

    private TrainerService trainerService;

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
            LOGGER.info("Future info {} {} {} {}", trainee3.getUser().getFirstName(), trainee3.getUser().getLastName(),
                    trainee3.getAddress(), trainee3.getDateOfBirth());

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

                trainerService.changePassword(authDTO, new ChangePasswordDTO(trainerId, "0987654321"));

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

                // Not Assigned trainers
                LOGGER.info("NOT ASSIGNED TRAINERS -  2");
                List<Trainer> notAssignedTrainers2 = trainingService.getNotAssignedTrainers(authDTO, "A.B");

                for (Trainer notAssignedTrainee : notAssignedTrainers2) {
                    LOGGER.info("{} ", notAssignedTrainee);
                }

                // Get trainee Trainings
                TraineeCriteriaDTO traineeCriteriaDTO = new TraineeCriteriaDTO();
                traineeCriteriaDTO.setUsername("A.B");

                List<Training> traineeTrainings0 = trainingService.getTraineeTrainings(authDTO, traineeCriteriaDTO);

                LOGGER.info("TRAINEE TRAININGS - 0");
                for (Training t : traineeTrainings0) {
                    LOGGER.info("{} ", t);
                }

                LOGGER.info("TRAINEE TRAININGS - 1");
                traineeCriteriaDTO.setFrom(LocalDate.now().plusDays(2));
                List<Training> traineeTrainings1 = trainingService.getTraineeTrainings(authDTO, traineeCriteriaDTO);
                for (Training t : traineeTrainings1) {
                    LOGGER.info("{} ", t);
                }

                LOGGER.info("TRAINEE TRAININGS - 2");
                traineeCriteriaDTO.setFrom(LocalDate.now().minusDays(2));
                List<Training> traineeTrainings2 = trainingService.getTraineeTrainings(authDTO, traineeCriteriaDTO);
                for (Training t : traineeTrainings2) {
                    LOGGER.info("{} ", t);
                }

                LOGGER.info("TRAINEE TRAININGS - 3");
                traineeCriteriaDTO.setTrainerName("C.C");
                List<Training> traineeTrainings3 = trainingService.getTraineeTrainings(authDTO, traineeCriteriaDTO);
                for (Training t : traineeTrainings3) {
                    LOGGER.info("{} ", t);
                }

                LOGGER.info("TRAINEE TRAININGS - 4");
                traineeCriteriaDTO.setTrainerName("B.B");
                List<Training> traineeTrainings4 = trainingService.getTraineeTrainings(authDTO, traineeCriteriaDTO);
                for (Training t : traineeTrainings4) {
                    LOGGER.info("{} ", t);
                }

                LOGGER.info("TRAINEE TRAININGS - 5");
                traineeCriteriaDTO.setTrainingType("Cycling");
                List<Training> traineeTrainings5 = trainingService.getTraineeTrainings(authDTO, traineeCriteriaDTO);
                for (Training t : traineeTrainings5) {
                    LOGGER.info("{} ", t);
                }

                LOGGER.info("TRAINEE TRAININGS - 6");
                traineeCriteriaDTO.setTrainerName("Swimming");
                List<Training> traineeTrainings6 = trainingService.getTraineeTrainings(authDTO, traineeCriteriaDTO);
                for (Training t : traineeTrainings6) {
                    LOGGER.info("{} ", t);
                }

                LOGGER.info("CHECK USERNAME  GENERATOR SERVICE");
                
                TraineeCreateDTO createDTO3 = new TraineeCreateDTO();
                createDTO3.setAddress("P");
                createDTO3.setDateOfBirth(LocalDate.now());
                createDTO3.setFirstName("A");
                createDTO3.setLastName("B");

                Optional<Trainee> optional3 = traineeService.create(createDTO3);

                LOGGER.info("Created user with same first name and second name {}", optional3.get());
              
            }

        }
            */
    

