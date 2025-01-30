package com.example.demo.dto.response;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TrainingTypeResponseDTO {
    private UUID id;
    private String trainingTypeName;
}
