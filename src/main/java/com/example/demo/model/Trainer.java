package com.example.demo.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;


@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
public class Trainer extends User{

    private String specialization;

    public Trainer(UUID userId , String firstName, String lastName,String username,
    String password, boolean active, String specialization) {
        super(userId , firstName , lastName , username , password, active);
        this.specialization = specialization;
    }

}
