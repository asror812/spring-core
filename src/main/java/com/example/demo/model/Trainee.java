package com.example.demo.model;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.util.ArrayList;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "trainees")
public class Trainee extends BaseEntity {

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    private String address;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToMany(mappedBy = "trainees", fetch = FetchType.EAGER)
    private List<Trainer> trainers = new ArrayList<>();

    @OneToMany(mappedBy = "trainee")
    private List<Training> trainings = new ArrayList<>();

    @Override
    public String toString() {
        return "Trainee [dateOfBirth=" + dateOfBirth + ", address=" + address + ", user=" + user.getUsername() + "]";
    }

}
