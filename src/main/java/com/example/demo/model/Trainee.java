package com.example.demo.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.UUID;


@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
public class Trainee  extends User{

    private LocalDate dateOfBirth;
    private String address;

    public Trainee(UUID userId , String firstName , String lastName , String username , String password ,
                   boolean active , LocalDate dateOfBirth , String address) {
        super(userId , firstName , lastName , username , password , active );
        this.dateOfBirth = dateOfBirth;
        this.address = address;
    }
}
