package com.example.demo.model;

import java.util.UUID;

public class Trainer extends User{

    private UUID userId;
    private String specialization;


    public Trainer() {
        super();
    }
    public Trainer( UUID userId, String firstName, String lastName, String username , String specialization  , String password , boolean active ) {
        super(firstName , lastName ,  username , password , active);
        this.userId = userId;
        this.specialization = specialization;
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


    public String toString() {
        return "Trainer [userId=" + userId + ", specialization=" + specialization + "]";
    }
}
