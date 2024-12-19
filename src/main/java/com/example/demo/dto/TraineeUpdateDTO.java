package com.example.demo.dto;

import java.time.LocalDate;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TraineeUpdateDTO  extends BaseDTO {
    
    private String   password;
    private boolean  active;
    private LocalDate dateOfBirth;
    private String address;

    public TraineeUpdateDTO(String firstName, String lastName, 
    String password, boolean active, LocalDate dateOfBirth, String address){
        super(firstName, lastName);
        this.password = password;
        this.active = active;
    } 
}
