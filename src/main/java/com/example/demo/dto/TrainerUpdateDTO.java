package com.example.demo.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TrainerUpdateDTO extends BaseDTO{

    private String password;
    private String specialization;
    
    public TrainerUpdateDTO(String firstName , String lastName , String password ,  String specialization){
        super(firstName, lastName);
        this.password = password;
        this.specialization = specialization;
    }
}
