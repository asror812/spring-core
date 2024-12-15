package com.example.demo.dao;

import com.example.demo.model.Trainee;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.*;


@Repository
@Getter
public class TraineeDAOImpl extends GenericDAOImpl<Trainee , UUID> implements TraineeDAO {

     private final Map<UUID, Trainee> storageMap;

     @Autowired
     public TraineeDAOImpl(@Qualifier("traineesMap") Map<UUID, Trainee> storageMap) {
         this.storageMap  = storageMap;
     }

    @Override
    public void update(Trainee trainee) {
         storageMap.put(trainee.getUserId(), trainee);
    }


    @Override
    public void delete(UUID id) {
        storageMap.remove(id);
    }

}
