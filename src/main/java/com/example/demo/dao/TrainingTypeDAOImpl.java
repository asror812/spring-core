package com.example.demo.dao;

import com.example.demo.model.TrainingType;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public class TrainingTypeDAOImpl extends AbstractHibernateDAO<TrainingType> implements TrainingTypeDAO {

    private static final String HQL_FIND_TRAINING_TYPE_BY_NAME = "FROM TrainingType WHERE trainingTypeName = :type";

    public TrainingTypeDAOImpl() {
        super(TrainingType.class);
    }

    @Override
    public Optional<TrainingType> findByName(String name) {
        TypedQuery<TrainingType> query = entityManager.createQuery(HQL_FIND_TRAINING_TYPE_BY_NAME, TrainingType.class);
        query.setParameter("type", name);
        return Optional.ofNullable(query.getSingleResult());
    }

}
