package com.example.demo.dao;
import com.example.demo.model.Trainer;

import org.springframework.stereotype.Repository;

@Repository
public interface TrainerDAO extends GenericDAO<Trainer> {
    
    void update(Trainer trainer);
   
}
