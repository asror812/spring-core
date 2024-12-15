package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TrainerCreateDTO extends BaseDTO {
  
    private String specialization;

    public TrainerCreateDTO(String firstName , String lastName ,String specialization){
         super(firstName , lastName);
         this.specialization = specialization;
    }
}
