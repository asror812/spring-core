package com.example.demo.service;


import com.example.demo.dao.TrainerDAO;
import com.example.demo.dto.TrainerCreateDTO;
import com.example.demo.dto.TrainerUpdateDTO;
import com.example.demo.model.Trainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class TrainerService {


    private final Logger logger = LoggerFactory.getLogger(TrainerService.class);
    private final TrainerDAO trainerDAO;

    @Autowired
    public TrainerService(TrainerDAO trainerDAO) {
        this.trainerDAO = trainerDAO;
    }

    public List<Trainer> findAll() {

        List<Trainer> trainers = trainerDAO.select();

        trainers.forEach(trainer -> {logger.info(trainer.toString());});

        return trainers;
    }

    public Trainer findById(UUID id) {
        Trainer trainer = trainerDAO.selectById(id);

        if(trainer == null) {
            logger.warn("Trainer with id {} not found", id);
        }

        else logger.info("Trainer with id {} found", id);

        return trainer;
    }


    public Trainer create(TrainerCreateDTO createDTO) {

        Trainer newTrainer = new Trainer();
        UUID userId = UUID.randomUUID();
        String username = newTrainer.getFirstName()+ "." + newTrainer.getLastName();

        newTrainer.setUserId(userId);
        newTrainer.setFirstName(createDTO.getFirstName());
        newTrainer.setLastName(createDTO.getLastName());
        newTrainer.setUsername(username);
        newTrainer.setSpecialization(createDTO.getSpecialization());

        for (Trainer trainer1 : trainerDAO.select()) {
             if(Objects.equals(trainer1.getFirstName(), createDTO.getFirstName())
                 && Objects.equals(trainer1.getLastName(), createDTO.getLastName())) {

                 newTrainer.setUsername(username + userId);

                 trainerDAO.create(newTrainer);

                 logger.info("Trainer with username {} successfully created . Specialization : {}" ,
                         newTrainer.getUsername() , newTrainer.getSpecialization());

                 return newTrainer;
             }
        }

        trainerDAO.create(newTrainer);

        logger.info("Trainer with username {} successfully created. Specialization: {} successfully created" ,
                username  ,  newTrainer.getSpecialization());


        return newTrainer;
    }

    public Trainer update(UUID id , TrainerUpdateDTO updateDTO) {
         Trainer trainer = trainerDAO.selectById(id);

         if(trainer == null) {
             logger.error("Trainer with id {} not found", id);
         }
         else {
             trainer.setFirstName(updateDTO.getFirstName());
             trainer.setLastName(updateDTO.getLastName());
             trainer.setPassword(updateDTO.getPassword());
             trainer.setSpecialization(updateDTO.getSpecialization());

             trainerDAO.update(trainer);
         }

         return trainer;
    }




}
