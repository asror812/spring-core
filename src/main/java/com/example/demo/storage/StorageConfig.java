package com.example.demo.storage;


import com.example.demo.model.Trainee;
import com.example.demo.model.Trainer;
import com.example.demo.model.Training;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.*;

@Configuration
public class StorageConfig {

    @Bean
    public Map<UUID , Trainee> traineesMap() {
        return new HashMap<>();
    }


    @Bean
    public Map<UUID , Trainer> trainersMap() {
        return new HashMap<>();
    }



    @Bean
    public Map<UUID , Training> trainingsMap() {
        return new HashMap<>();
    }

}
