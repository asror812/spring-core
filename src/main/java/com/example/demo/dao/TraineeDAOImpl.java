package com.example.demo.dao;

import com.example.demo.model.Trainee;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public class TraineeDAOImpl extends AbstractHibernateDAO<Trainee> implements TraineeDAO {
    private static final String FIND_TRAINEE_BY_USERNAME_QUERY = "FROM Trainee WHERE user.username = :username";

    public TraineeDAOImpl() {
        super(Trainee.class);
    }

    @Override
    public Optional<Trainee> findByUsername(String username) {
        TypedQuery<Trainee> query = entityManager.createQuery(FIND_TRAINEE_BY_USERNAME_QUERY, Trainee.class);
        query.setParameter("username", username);
        return Optional.ofNullable(query.getSingleResult());
    }

    //optimize method 
    @Override
    public void delete(Trainee trainee) {
        if (entityManager.contains(trainee)) {
            entityManager.remove(trainee);
        }
    }
}
