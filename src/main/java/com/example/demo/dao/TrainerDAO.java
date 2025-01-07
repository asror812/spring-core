package com.example.demo.dao;

import com.example.demo.model.Trainer;
import java.util.Optional;
import java.util.UUID;
import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public interface TrainerDAO {
    
    Optional<Trainer> create(Trainer trainer);

    public Optional<Trainer> findById(UUID id);
    
    Optional<Trainer> findByUsername(String username);
    
    void update(Trainer trainer);
   
    List<Trainer> getAll();
}
