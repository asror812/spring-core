package com.example.demo.dao;

import com.example.demo.model.Trainee;
import com.example.demo.model.Trainer;
import com.example.demo.model.Training;
import com.example.demo.model.TrainingType;
import com.example.demo.model.User;
import com.example.demo.service.AuthService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.*;

@Repository
public class TrainingDAOImpl implements TrainingDAO {

    @PersistenceContext
    private EntityManager entityManager;
    private static final String HQL_GET_ALL_TRAININGS = "from Training";

    private static final String FIND_TRAINING_BY_TRAINEE_ID = "FROM Training T WHERE T.trainee.id = :id";



    private static final Logger LOGGER = LoggerFactory.getLogger(AuthService.class);

    public Optional<Training> create(Training training) {
        try {
            entityManager.persist(training);
            return Optional.of(training);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public List<Training> findTraineeTrainingsById(UUID id) {
        TypedQuery<Training> query = entityManager.createQuery(FIND_TRAINING_BY_TRAINEE_ID, Training.class);
        query.setParameter("id", id);

        List<Training> resultList = query.getResultList();

        return resultList;
    }

    @Override
    public List<Training> findTraineeTrainings(String username, LocalDate from, LocalDate to, String trainerName,
            String trainingTypeName) {

        try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<Training> query = cb.createQuery(Training.class);
            Root<Training> training = query.from(Training.class);

            // Joins
            Join<Training, Trainee> trainee = training.join("trainee");
            Join<Trainee, User> traineeUser = trainee.join("user");
            Join<Training, Trainer> trainer = training.join("trainer");
            Join<Trainer, User> trainerUser = trainer.join("user");
            Join<Training, TrainingType> type = training.join("trainingType");

            // Predicates (Filters)
            List<Predicate> predicates = new ArrayList<>();

            // Mandatory Filter: Trainee Username
            predicates.add(cb.equal(traineeUser.get("username"), username));

            // Optional Filters
            if (from != null) {
                predicates.add(cb.greaterThanOrEqualTo(training.get("trainingDate"), from));
            }

            if (to != null) {
                predicates.add(cb.lessThanOrEqualTo(training.get("trainingDate"), to));
            }

            if (trainerName != null && !trainerName.trim().isEmpty()) {
                predicates.add(cb.equal(trainerUser.get("username"), trainerName));
            }

            if (trainingTypeName != null && !trainingTypeName.trim().isEmpty()) {
                predicates.add(cb.equal(type.get("trainingTypeName"), trainingTypeName));
            }

            query.where(predicates.toArray(new Predicate[0]));

            return entityManager.createQuery(query).getResultList();
        } catch (Exception e) {
            LOGGER.error("Failed to get Trainee Trainings : {}", username);

            return Collections.emptyList();
        }
    }

    @Override
    public List<Training> findTrainerTrainings(String username, LocalDate from, LocalDate to, String traineeName) {
        try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<Training> query = cb.createQuery(Training.class);
            Root<Training> training = query.from(Training.class);

            // Joins
            Join<Training, Trainee> trainee = training.join("trainee");
            Join<Trainee, User> traineeUser = trainee.join("user");
            Join<Training, Trainer> trainer = training.join("trainer");
            Join<Trainer, User> trainerUser = trainer.join("user");

            // Predicates
            List<Predicate> predicates = new ArrayList<>();

            // Mandatory Filter: Trainer Username
            predicates.add(cb.equal(trainerUser.get("username"), username));

            if (from != null) {
                predicates.add(cb.greaterThanOrEqualTo(training.get("trainingDate"), from));
            }

            if (to != null) {
                predicates.add(cb.lessThanOrEqualTo(training.get("trainingDate"), to));
            }

            if (traineeName != null && !traineeName.trim().isEmpty()) {
                predicates.add(cb.equal(traineeUser.get("username"), traineeName));
            }

            query.where(predicates.toArray(new Predicate[0]));

            return entityManager.createQuery(query).getResultList();
        } catch (Exception e) {

            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    @Override
    public List<Training> getAll() {
        
        TypedQuery<Training> query = entityManager.createQuery(HQL_GET_ALL_TRAININGS, Training.class);

        List<Training> results = query.getResultList();

        return results;
    }
}
