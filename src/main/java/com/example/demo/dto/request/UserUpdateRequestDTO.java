package com.example.demo.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateRequestDTO {

   @NotBlank(message = "Username must not be empty")
   private String username;

   @NotBlank(message = "First name must not be empty")
   private String firstName;

   @NotBlank(message = "Last name must not be empty")
   private String lastName;

   @NotNull(message = "Active status must not be null")
   private Boolean active;
}
