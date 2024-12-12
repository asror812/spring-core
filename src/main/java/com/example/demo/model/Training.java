package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

public class Training {

    private UUID traineeId;
    private UUID trainerId;
    private String trainingName;
    private String trainingType;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate trainingDate;
    private Double trainingDuration;



    public Training() {}

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

   public String getTrainingName() {
       return trainingName;
   }

   public void setTrainingName(String trainingName) {
       this.trainingName = trainingName;
   }
   public String getTrainingType() {
       return trainingType;
   }
   public void setTrainingType(String trainingType) {
       this.trainingType = trainingType;
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


   public String toString() {
        return "Training [traineeId=" + traineeId + ", trainerId=" + trainerId + ", trainingName=" +
                trainingName + ", trainingType=" + trainingType + ", trainingDate=" + trainingDate +
                ", trainingDuration=" + trainingDuration + "]";
   }
}
