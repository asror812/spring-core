package com.example.demo.service;


import com.example.demo.dao.TrainingDAO;
import com.example.demo.model.Trainer;
import com.example.demo.model.Training;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TrainingService {

    private final TrainingDAO trainingDAO;
    private final Logger logger = LoggerFactory.getLogger(TrainingService.class);

    @Autowired
    public TrainingService(TrainingDAO trainingDAO) {
        this.trainingDAO = trainingDAO;
    }

    public List<Training> findAll() {

        List<Training> trainings = trainingDAO.select();

        trainings.forEach(training -> {logger.info(training.toString());});

        return trainings;
    }

    public Training findById(UUID id) {
        Training trainer = trainingDAO.selectById(id);

        if(trainer == null) {
            logger.warn("Trainer with id {} not found", id);
        }

        else logger.info("Trainer with id {} found", id);

        return trainer;
    }

    public Training save(Training training) {
        //todo
    }




}
