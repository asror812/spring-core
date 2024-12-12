package com.example.demo.dao;

import com.example.demo.model.Trainer;
import com.example.demo.model.Training;

import java.util.List;
import java.util.UUID;

public interface TrainingDAO {

    void create(Training training);
    List<Training> select();
    Training selectById(UUID id);

}
