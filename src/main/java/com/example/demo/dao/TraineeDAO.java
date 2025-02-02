package com.example.demo.dao;


import com.example.demo.model.Trainee;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Repository
public interface TraineeDAO {

    Optional<Trainee> create(Trainee trainee);
    
    void update(Trainee trainee);

    void delete(Trainee trainee);

    public Optional<Trainee> findById(UUID id);
    
    Optional<Trainee> findByUsername(String username);


    List<Trainee> getAll();
}
