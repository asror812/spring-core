package com.example.demo.dao;

import com.example.demo.model.Training;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import java.util.*;


@Repository
@Getter
public class TrainingDAOImpl extends GenericDAOImpl<Training>  {

    private final Map<UUID, Training> storageMap;

    @Autowired
    public TrainingDAOImpl(@Qualifier("trainingsMap") Map<UUID, Training> storageMap) {
        this.storageMap = storageMap;
    }
}
