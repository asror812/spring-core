package com.example.demo.dto.request;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TrainerUpdateRequestDTO extends UserUpdateRequestDTO {

    private UUID specialization;
}