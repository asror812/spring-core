package com.example.demo.dao;

import com.example.demo.model.Trainer;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public class TrainerDAOImpl extends AbstractHibernateDAO<Trainer> implements TrainerDAO {
    private static final String HQL_FIND_TRAINER_BY_USERNAME = "FROM Trainer WHERE user.username = :username";
    private static final String HQL_GET_ALL = "FROM Trainer";

    public TrainerDAOImpl() {
        super(Trainer.class);
    }

    @Override
    public Optional<Trainer> findByUsername(String username) {
        TypedQuery<Trainer> query = entityManager.createQuery(HQL_FIND_TRAINER_BY_USERNAME, Trainer.class);
        query.setParameter("username", username);
        return Optional.ofNullable(query.getSingleResult());
    }
    
    @Override
    public List<Trainer> getAll() {
        TypedQuery<Trainer> query = entityManager.createQuery(HQL_GET_ALL, Trainer.class);
        return query.getResultList();
    }
}
