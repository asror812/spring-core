package com.example.demo.dao;

import com.example.demo.model.TrainingType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public class TrainingTypeDAOImpl implements TrainingTypeDAO {

    @PersistenceContext
    private EntityManager entityManager;

    private static final String HQL_FIND_TRAINING_TYPE_BY_NAME = "FROM TrainingType tt WHERE tt.trainingTypeName = :type";
    private static final String HQL_GET_ALL_TRAINING_TYPES = "from TrainingType";


    public List<TrainingType> getAll() {
        
        TypedQuery<TrainingType> query = entityManager.createQuery(HQL_GET_ALL_TRAINING_TYPES, TrainingType.class);

        List<TrainingType> results = query.getResultList();

        return results;
    }
    
    @Override
    public Optional<TrainingType> findByName(String name) {
        try {

            TypedQuery<TrainingType> tQuery = entityManager.createQuery(HQL_FIND_TRAINING_TYPE_BY_NAME, TrainingType.class);
            tQuery.setParameter("type", name);

            TrainingType trainingType = tQuery.getSingleResult();

            return Optional.of(trainingType);

        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<TrainingType> findById(UUID id) {
        TrainingType trainingType = entityManager.find(TrainingType.class, id);

        if (trainingType == null) {
            return Optional.empty();
        }

        return Optional.of(trainingType);
    }

    @Override
    public Optional<TrainingType> create(TrainingType trainingType) {

        try {
            entityManager.persist(trainingType);

            return Optional.of(trainingType);
        } catch (Exception e) {
            return Optional.empty();
        }

    }
}
