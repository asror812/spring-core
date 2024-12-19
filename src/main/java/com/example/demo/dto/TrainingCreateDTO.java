package com.example.demo.dto;


import java.time.LocalDate;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
public class TrainingCreateDTO {
    private UUID traineeId;
    private UUID trainerId;
    private String trainingName;
    private String trainingType;
    private LocalDate trainingDate;
    private Double trainingDuration;

}
