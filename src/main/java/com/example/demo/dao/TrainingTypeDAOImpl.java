package com.example.demo.dao;

import com.example.demo.model.TrainingType;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public class TrainingTypeDAOImpl extends AbstractHibernateDAO<TrainingType> implements TrainingTypeDAO {


    private static final String HQL_FIND_TRAINING_TYPE_BY_NAME = "FROM TrainingType tt WHERE tt.trainingTypeName = :type";

    public TrainingTypeDAOImpl() {
        super(TrainingType.class);
    }
    
    @Override
    public Optional<TrainingType> findByName(String name) {
        try {
            TypedQuery<TrainingType> query = entityManager.createQuery(HQL_FIND_TRAINING_TYPE_BY_NAME, TrainingType.class);
            query.setParameter("type", name);

            TrainingType trainingType = query.getSingleResult();

            return Optional.of(trainingType);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

}
