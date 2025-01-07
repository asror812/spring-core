package com.example.demo.dto;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter@Setter
@ToString(callSuper = true)
public class TraineeCreateDTO extends UserCreateDTO {

    private LocalDate dateOfBirth;

    private String address;

}