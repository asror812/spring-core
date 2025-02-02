package com.example.demo.dao;


import com.example.demo.model.TrainingType;
import org.springframework.stereotype.Repository;
import java.util.*;


@Repository
public interface TrainingTypeDAO  {

    Optional<TrainingType> findByName(String name);

    Optional<TrainingType> findById(UUID id);

    Optional<TrainingType> create(TrainingType trainingType);

    List<TrainingType> getAll();
}
