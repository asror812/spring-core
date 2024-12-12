package com.example.demo.model;

import java.util.UUID;

public class Trainer extends User{

    private UUID userId;
    private String specialization;

    public Trainer(UUID userId , String specialization , String firstName, String lastName, boolean active) {
        super(firstName, lastName , active);
        this.userId = userId;
        this.specialization = specialization;
    }

    public Trainer() {
        super();
    }


    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

}
