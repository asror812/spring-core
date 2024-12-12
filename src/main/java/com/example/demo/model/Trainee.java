package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.util.UUID;

public class Trainee  extends User{

    private UUID   userId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;
    private String address;


    public Trainee() {}

    public Trainee(UUID userId, String firstName , String lastName , String username, String password, boolean active , LocalDate dateOfBirth, String address ) {
        super(firstName , lastName , username , password ,  active);
        this.userId = userId;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String toString() {
        return "Trainee [userId=" + userId + ", dateOfBirth=" + dateOfBirth + ", address=" + address + "]";
    }
}
