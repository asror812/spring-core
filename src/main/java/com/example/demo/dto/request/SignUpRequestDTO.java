package com.example.demo.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpRequestDTO {
 
   @NotBlank
   private String firstName;
   
   @NotBlank
   private String lastName;

}
