package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.util.UUID;

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

    public UUID getTraineeId() {
        return traineeId;
    }

    public void setTraineeId(UUID traineeId) {
        this.traineeId = traineeId;
    }

    public UUID getTrainerId() {
        return trainerId;
    }

    public void setTrainerId(UUID trainerId) {
        this.trainerId = trainerId;
    }

    public String getTrainingType() {
        return trainingType;
    }

    public void setTrainingType(String trainingType) {
        this.trainingType = trainingType;
    }

    public String getTrainingName() {
        return trainingName;
    }

    public void setTrainingName(String trainingName) {
        this.trainingName = trainingName;
    }

    public LocalDate getTrainingDate() {
        return trainingDate;
    }

    public void setTrainingDate(LocalDate trainingDate) {
        this.trainingDate = trainingDate;
    }

    public Double getTrainingDuration() {
        return trainingDuration;
    }

    public void setTrainingDuration(Double trainingDuration) {
        this.trainingDuration = trainingDuration;
    }
}
