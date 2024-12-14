package com.example.demo.dto;


import java.time.LocalDate;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class TrainingCreateDTO {
    private UUID traineeId;
    private UUID trainerId;
    private String trainingName;
    private String trainingType;

    private LocalDate trainingDate;
    private Double trainingDuration;


    public TrainingCreateDTO(UUID trainerId , UUID traineeId, String trainingName, String trainingType, LocalDate trainingDate, Double trainingDuration) {
        this.trainerId = trainerId;
        this.traineeId = traineeId;
        this.trainingName = trainingName;
        this.trainingType = trainingType;
        this.trainingDate = trainingDate;
        this.trainingDuration = trainingDuration;
    }

}
