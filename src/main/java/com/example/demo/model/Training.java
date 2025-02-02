package com.example.demo.model;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "trainings")
@Getter@Setter
public class Training extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "trainee_id")
    private Trainee trainee;

    @ManyToOne
    @JoinColumn(name = "trainer_id")
    private Trainer trainer;

    @Column(name = "training_name", nullable = false)
    private String trainingName;

    @ManyToOne
    @JoinColumn(name = "training_type_id")
    private TrainingType trainingType;

    @Column(name = "training_date", nullable = false)
    private LocalDate trainingDate;

    @Column(name = "duration", nullable = false)
    private Double duration;

    @Override
    public String toString() {
        return "Training [trainee=" + trainee.getUser().getUsername() + ", trainer=" + trainer.getUser().getUsername() + ", trainingName=" + trainingName
                + ", trainingType=" + trainingType + ", trainingDate=" + trainingDate + ", duration=" + duration + "]";
    }

    

}
