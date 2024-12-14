package com.example.demo.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.UUID;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Training {

    private UUID traineeId;
    private UUID trainerId;
    private String trainingName;
    private String trainingType;

    private LocalDate trainingDate;
    private Double trainingDuration;




}
