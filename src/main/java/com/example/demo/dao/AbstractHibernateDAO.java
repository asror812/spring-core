package com.example.demo.dao;

import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import com.example.demo.exceptions.DataAccessException;

@Repository
public abstract class AbstractHibernateDAO<T> implements GenericDAO<T> {

    @PersistenceContext
    protected EntityManager entityManager;

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractHibernateDAO.class);

    private final Class<T> clazz;

    protected AbstractHibernateDAO(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public Optional<T> findById(UUID id) {
        return Optional.ofNullable(entityManager.find(clazz, id));
    }

    @Override
    public Optional<T> create(T entity) {
        try {
            entityManager.persist(entity);

            return Optional.of(entity);
        } catch (Exception e) {
            LOGGER.info("Failed to create {}", entity);
            throw new DataAccessException("Failed to create " + entity);
        }
    }

    @Override
    public void update(T entity) {
        try {
            entityManager.merge(entity);
        } catch (Exception e) {
            LOGGER.error("Failed to update {}", entity);
            throw new DataAccessException("Failed to update " + entity);
        }
    }

}
