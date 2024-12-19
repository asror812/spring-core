package com.example.demo.dao;

import com.example.demo.model.Trainee;
import org.springframework.stereotype.Repository;

import java.util.UUID;


@Repository
public interface TraineeDAO extends GenericDAO<Trainee> {
    void update(Trainee trainee);
    void delete(UUID id);
}
