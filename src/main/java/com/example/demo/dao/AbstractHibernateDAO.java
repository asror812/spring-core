package com.example.demo.dao;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.example.demo.exceptions.DataAccessException;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceException;

@Repository
public abstract class AbstractHibernateDAO<T> {

    @PersistenceContext
    protected EntityManager entityManager;
    private final Class<T> clazz;

    protected AbstractHibernateDAO(Class<T> clazz) {
        this.clazz = clazz;
    }

    public Optional<T> findById(UUID id) {
        try {
            return Optional.ofNullable(entityManager.find(clazz, id));
        } catch (PersistenceException ex) {
            throw new DataAccessException("Error accessing the database", ex);
        }
    }

    public T create(T entity) {
        entityManager.persist(entity);
        return entity;
    }

    public void update(T entity) {
        entityManager.merge(entity);
    }

}
