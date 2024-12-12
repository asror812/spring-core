package com.example.demo.dao;

import com.example.demo.model.Trainee;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;


@Component
public interface TraineeDAO {

    void create(Trainee trainee);
    List<Trainee> select();
    Trainee selectById(UUID id);

    void update(Trainee trainee);
    void delete(UUID id);
}
