package com.example.demo.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "trainers")
@Getter@Setter
@NoArgsConstructor
public class Trainer extends BaseEntity  {

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "specialization")
    private TrainingType specialization;

    @OneToMany(mappedBy = "trainer", cascade = { CascadeType.PERSIST, CascadeType.MERGE }, orphanRemoval = true)
    private List<Training> trainings = new ArrayList<>();

    @Override
    public String toString() {
        return "Trainer [user=" + user .getUsername() +  " specialization=" + specialization + "]";
    }

    
  
}
