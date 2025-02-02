package com.example.demo.dto;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TrainerCreateDTO extends UserCreateDTO {

    @NotNull
    private UUID trainingTypeId;

}