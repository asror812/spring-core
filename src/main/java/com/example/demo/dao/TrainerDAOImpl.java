package com.example.demo.dao;

import com.example.demo.model.Trainer;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
@Getter
public class TrainerDAOImpl extends GenericDAOImpl<Trainer> implements TrainerDAO {

    private final Map<UUID, Trainer> storageMap ;

    @Autowired
    public TrainerDAOImpl(@Qualifier("trainersMap") Map<UUID, Trainer> storageMap) {
        this.storageMap = storageMap;
    }

    @Override
    public void update(Trainer trainer) {
        storageMap.put(trainer.getUserId(), trainer);
    }

}
