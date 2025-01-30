package com.example.demo.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TrainerResponseDTO {

    private UserUpdateResponseDTO user;
    private TrainingTypeResponseDTO specialization;
}
