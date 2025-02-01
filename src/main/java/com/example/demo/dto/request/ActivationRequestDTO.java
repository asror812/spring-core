package com.example.demo.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ActivationRequestDTO {

   @NotBlank(message = "Username must not be empty")
   private String username;

   @NotNull(message = "Active status must not be empty")
   private Boolean active;
}
