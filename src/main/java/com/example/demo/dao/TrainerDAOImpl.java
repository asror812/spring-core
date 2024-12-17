package com.example.demo.dao;

import com.example.demo.model.Trainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class TrainerDAOImpl implements TrainerDAO {

    private final Map<UUID , Trainer> trainersMap ;

    @Autowired
    public TrainerDAOImpl(Map<UUID , Trainer> trainersMap) {
        this.trainersMap = trainersMap;
    }


    @Override
    public void create(Trainer trainer) {
        trainersMap.putIfAbsent(trainer.getUserId() , trainer);
    }

    @Override
    public List<Trainer> select() {
        return new ArrayList<>(trainersMap.values());
    }

    @Override
    public Trainer selectById(UUID id) {
        return trainersMap.get(id);
    }

    @Override
    public void update(Trainer trainer) {
         trainersMap.put(trainer.getUserId() , trainer);
    }

}
