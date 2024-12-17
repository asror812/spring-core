package com.example.demo.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class TrainingType {

    private String trainingTypeName;

    public TrainingType(String trainingTypeName) {
        this.trainingTypeName = trainingTypeName;
    }

    public String getTrainingType() {
        return trainingTypeName;
    }
    public void setTrainingType(String trainingTypeName) {
        this.trainingTypeName = trainingTypeName;
    }
}
