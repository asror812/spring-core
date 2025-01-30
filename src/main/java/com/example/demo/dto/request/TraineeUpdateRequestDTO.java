package com.example.demo.dto.request;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TraineeUpdateRequestDTO extends UserUpdateRequestDTO {
    
    private Date dateOfBirth;
    private String address;
}