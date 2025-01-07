package com.example.demo.dto;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChangePasswordDTO {

   @NotNull
   private UUID id;

   @NotNull
   @Size(min = 10, message = "Password must be at least 10 characters long")
   private String newPassword;
      
}
