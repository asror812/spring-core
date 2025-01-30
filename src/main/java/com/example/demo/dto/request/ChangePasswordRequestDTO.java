package com.example.demo.dto.request;


import jakarta.validation.constraints.NotBlank;
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
public class ChangePasswordRequestDTO {

   @NotBlank
   private String username;

   @NotBlank
   @Size(min = 8, message = "Password must be at least 8 characters long")
   private String oldPassword;

   @NotNull
   @Size(min = 8, message = "Password must be at least 8 characters long")
   private String newPassword;
      
}
