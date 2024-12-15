package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TrainerUpdateDTO extends BaseDTO{

    private String password;
    private String specialization;
    
    public TrainerUpdateDTO(String firstName , String lastName , String password ,  String specialization){
        super(firstName, lastName);
        this.password = password;
        this.specialization = specialization;
    }
}
