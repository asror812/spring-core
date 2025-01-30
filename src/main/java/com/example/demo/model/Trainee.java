package com.example.demo.model;

import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;
import java.util.Date;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "trainees")
public class Trainee extends BaseEntity {

    @Column(name = "date_of_birth")
    @Temporal(TemporalType.DATE)
    private Date dateOfBirth;

    private String address;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "trainee")
    private List<Training> trainings = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "trainee_trainers",
                joinColumns = @JoinColumn(name = "trainee_id"),
            inverseJoinColumns = @JoinColumn(name = "trainer_id"))
    private List<Trainer> trainers = new ArrayList<>();            

    @Override
    public String toString() {
        return "Trainee [dateOfBirth=" + dateOfBirth + ", address=" + address + ", user=" + user.getUsername() + "]";
    }

}
