package com.example.demo.dao;

import com.example.demo.model.Training;
import org.springframework.stereotype.Component;

import java.util.*;


@Component
public class TrainingDAOImpl implements TrainingDAO {

    private final Map<UUID, Training> trainings;

    public TrainingDAOImpl(Map<UUID, Training> trainings) {
        this.trainings = new HashMap<>();
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
    public Training selectById(int id) {

        return null;
    }
}
