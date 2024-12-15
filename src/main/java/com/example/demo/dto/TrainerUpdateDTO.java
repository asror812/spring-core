package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TrainerUpdateDTO extends BaseDTO{
    private String specialization;
    
    public TrainerUpdateDTO(String firstName , String lastName , String specialization){
        super(firstName, lastName);
        this.specialization = specialization;
    }
}
