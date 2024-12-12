package com.example.demo.dao;

import com.example.demo.model.Trainer;

import java.util.List;
import java.util.UUID;

public interface TrainerDAO {

    void create(Trainer trainer);
    List<Trainer> select();
    Trainer selectById(UUID id);

    void update(Trainer trainer);

}
