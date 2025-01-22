package com.example.demo.dao;

import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Repository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public abstract class AbstractHibernateDAO<T> implements GenericDAO<T> {

    @PersistenceContext
    protected EntityManager entityManager;
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
        entityManager.persist(entity);
        return Optional.of(entity);
    }

    @Override
    public void update(T entity) {
        entityManager.merge(entity);
    }

}
