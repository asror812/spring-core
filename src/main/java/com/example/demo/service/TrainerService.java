package com.example.demo.service;


import com.example.demo.dao.TrainerDAO;
import com.example.demo.model.Trainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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





}
