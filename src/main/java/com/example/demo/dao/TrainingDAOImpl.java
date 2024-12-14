package com.example.demo.dao;

import com.example.demo.model.Training;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;


@Component
public class TrainingDAOImpl implements TrainingDAO {

    private final Map<UUID, Training> trainings;

    @Autowired
    public TrainingDAOImpl(Map<UUID, Training> trainings) {
        this.trainings = trainings;
    }


    @Override
    public void create(Training training) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Training> select() {
        return new ArrayList<>(trainings.values());
    }

    @Override
    public Training selectById(UUID id) {
        return trainings.get(id);
    }
}
