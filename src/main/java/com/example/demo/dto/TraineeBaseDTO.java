package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class TraineeBaseDTO {

    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String address;
    private String password;
}
