package com.example.demo.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateRequestDTO {

   @NotBlank
   private String username;

   @NotBlank
   private String firstName;

   @NotBlank
   private String lastName;

   @NotNull
   private Boolean active;
}
