package com.example.demo.dto;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TraineeCreateDTO extends BaseDTO{
     
    private LocalDate dateOfBirth;
    private String address;

     public TraineeCreateDTO(String firstName , String lastName , String address , LocalDate dateOfBirth){
         super(firstName, lastName);
         this.address = address;
         this.dateOfBirth = dateOfBirth;
     }    
}
