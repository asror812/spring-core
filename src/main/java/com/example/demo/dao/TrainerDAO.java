package com.example.demo.dao;

import com.example.demo.model.Trainer;
import java.util.UUID;

import org.springframework.stereotype.Repository;

@Repository
public interface TrainerDAO extends GenericDAO<Trainer , UUID> {
    
    void update(Trainer trainer);
   
}
