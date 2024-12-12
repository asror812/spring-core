package com.example.demo.service;


import com.example.demo.dao.TraineeDAO;
import com.example.demo.dto.TraineeCreateDTO;
import com.example.demo.dto.TraineeUpdateDTO;
import com.example.demo.model.Trainee;
import com.example.demo.storage.PasswordGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class TraineeService {


    private final Logger logger = LoggerFactory.getLogger(TraineeService.class);

    private PasswordGenerator passwordGenerator;


    private final TraineeDAO traineeDAO;

    public TraineeService(TraineeDAO traineeDAO) {
        this.traineeDAO = traineeDAO;
    }


    public Trainee findById(UUID id) {
        Trainee trainee = traineeDAO.selectById(id);

        if(trainee == null)  logger.warn("Trainee with id {} not found", id);

        else logger.info("Trainee with id {} found", id);

        return trainee;
    }

    public List<Trainee> findAll() {
        List<Trainee> trainees = traineeDAO.select();

        for (Trainee trainee : trainees) {
            logger.info(trainee.toString());
        }

        return trainees;
    }

    public void delete(UUID id) {
        traineeDAO.delete(id);
    }

    public Trainee create(TraineeCreateDTO createDTO) {
        Trainee newTrainee = new Trainee();
        UUID id = UUID.randomUUID();
        String userName = newTrainee.getFirstName()  + "." + newTrainee.getLastName();
        String password = passwordGenerator.generate();


        newTrainee.setUserId(id);
        newTrainee.setFirstName(createDTO.getFirstName());
        newTrainee.setLastName(createDTO.getLastName());
        newTrainee.setUsername(userName);
        newTrainee.setActive(true);
        newTrainee.setAddress(createDTO.getAddress());
        newTrainee.setDateOfBirth(createDTO.getDateOfBirth());
        newTrainee.setPassword(password);


        for (Trainee trainee1 : traineeDAO.select()) {
            if(Objects.equals(trainee1.getFirstName(), createDTO.getFirstName())
                && Objects.equals(trainee1.getLastName(), createDTO.getLastName())
               ){

                newTrainee.setUsername(userName + newTrainee.getUserId());
                traineeDAO.create(newTrainee);

                logger.info("Trainer with username {}  successfully created. Password : {} ", newTrainee.getUsername() , password);

                return newTrainee;
            }
        }


        traineeDAO.create(newTrainee);

        logger.info("Trainer with username {} successfully created. Password : {}",  userName , password );

        return newTrainee;
    }

    @Autowired
    public void setPasswordGenerator(PasswordGenerator passwordGenerator) {
        this.passwordGenerator = passwordGenerator;
    }

    public void update(UUID id , TraineeUpdateDTO updateDTO) {
        Trainee trainee = traineeDAO.selectById(id);

        if(trainee == null)  logger.error("Trainee with id {} not found", id);

        trainee.setFirstName(updateDTO.getFirstName());
        trainee.setLastName(updateDTO.getLastName());
        trainee.setPassword(updateDTO.getPassword());
        trainee.setAddress(updateDTO.getAddress());



        traineeDAO.update(trainee);
    }


}
