package com.example.demo.dao;

import com.example.demo.model.Trainee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.*;


@Component
public class TraineeDAOImpl implements TraineeDAO {

     private final Map<UUID, Trainee> traineesMap;

     @Autowired
     public TraineeDAOImpl(Map<UUID, Trainee> traineesMap) {
         this.traineesMap  = traineesMap;
     }


     @Override
     public void create(Trainee trainee) {

         Trainee trainee1 = traineesMap.get(trainee.getUserId());


         if(trainee1 == null) {
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

         traineesMap.remove(trainee.getUserId());
         traineesMap.put(trainee.getUserId(), trainee);
    }

    @Override
    public void delete(UUID id) {
          traineesMap.remove(id);
    }

}
