package com.example.demo.dto.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SingUpResponseDTO {

   private String username;

   private String password;

   private String token;
}