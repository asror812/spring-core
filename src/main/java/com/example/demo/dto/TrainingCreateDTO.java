package com.example.demo.dto;

import java.time.LocalDate;
import java.util.UUID;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class TrainingCreateDTO {

   @NotNull
   private UUID traineeId;
   @NotNull
   private UUID trainerId;
   @NotNull
   private UUID trainingTypeId;
   @NotNull
   private LocalDate trainingDate;
   @NotNull
   private Double duration;

}