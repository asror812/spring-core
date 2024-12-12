package com.example.demo.dao;

import com.example.demo.model.Trainee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;


@Component
public class TraineeDAOImpl implements TraineeDAO {

     private final Logger logger = LoggerFactory.getLogger(TraineeDAOImpl.class);
     private final Map<UUID, Trainee> traineesMap;

     @Autowired
     public TraineeDAOImpl(Map<UUID, Trainee> traineesMap) {
         this.traineesMap  = traineesMap;
     }


     @Override
     public void create(Trainee trainee) {

         Trainee trainee1 = traineesMap.get(trainee.getUserId());


         if(trainee1 == null) {
             logger.info("Trainee " + trainee.getUserId() + " successfully created");
             traineesMap.put(trainee.getUserId(), trainee);
         }
         else {

         }

     }

    @Override
    public List<Trainee> select() {
        return new ArrayList<>(traineesMap.values());
    }

    @Override
    public Trainee selectById(UUID id) {

        return traineesMap.get(id) ;
    }

    @Override
    public void update(Trainee trainee) {
         //todo
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void delete(UUID id) {
          traineesMap.remove(id);
    }

}
