package com.example.demo.storage;


import com.example.demo.model.Training;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;


@Component
public class TrainingsMapInitializer {


    @Value("${trainings.file.path}")
    private String path;

    private final Logger LOGGER = LoggerFactory.getLogger(TrainingsMapInitializer.class);

    private final Map<UUID , Training> trainingsMap;

    @Autowired
    public TrainingsMapInitializer(Map<UUID, Training> trainingsMap) {
        this.trainingsMap = trainingsMap;
    }


    @PostConstruct
    public void initialize() {
        File file = new File(path);

        if(file.exists()) {
            try{
                LOGGER.info("Инициализация начинается");
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.registerModule(new JavaTimeModule());
                List<Training> trainers = objectMapper.readValue(new File(path) , new TypeReference<>() {});

                for (Training trainee : trainers) {
                    UUID primaryKey = UUID.randomUUID();
                    trainingsMap.put(primaryKey, trainee);
                }

                LOGGER.info("Инициализация прошла успешно");
            }catch (IOException e){
                LOGGER.error("Ошибка при чтении файла: {}", e.getMessage());
            }
        }else LOGGER.error("Файл не найден");

    }
}
