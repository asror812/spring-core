package com.example.demo.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ActivationRequestDTO {

   @NotBlank
   private String username;
   @NotNull
   private Boolean isActive;
}
