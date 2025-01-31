package com.example.demo.dto.request;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TrainerUpdateRequestDTO extends UserUpdateRequestDTO {

    @NotNull(message = "Specialization must not be null")
    private UUID specialization;
}