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


    public List<TrainingType> getAll() {
        final String hql = "from TrainingType ";
        TypedQuery<TrainingType> query = entityManager.createQuery(hql, TrainingType.class);

        List<TrainingType> results = query.getResultList();

        return results;
    }
    
    @Override
    public Optional<TrainingType> findByName(String name) {
        try {

            String hql = "FROM TrainingType tt WHERE tt.trainingTypeName = :type";
            TypedQuery<TrainingType> tQuery = entityManager.createQuery(hql, TrainingType.class);
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
