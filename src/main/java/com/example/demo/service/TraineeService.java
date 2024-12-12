package com.example.demo.service;


import com.example.demo.dao.TraineeDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class TraineeService {


    private final Logger logger = LoggerFactory.getLogger(TraineeService.class);

    private final TraineeDAO traineeDAO;

    public TraineeService(TraineeDAO traineeDAO) {
        this.traineeDAO = traineeDAO;
    }


}
