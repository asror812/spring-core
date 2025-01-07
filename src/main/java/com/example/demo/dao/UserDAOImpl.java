package com.example.demo.dao;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import com.example.demo.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;


@Repository
public class UserDAOImpl implements UserDAO {

    private static final String HQL_FIND_USER_BY_USERNAME = "FROM User U WHERE U.username = :username";

    private static final Logger LOGGER = LoggerFactory.getLogger(UserDAOImpl.class);
    
   
    @PersistenceContext
    private EntityManager entityManager;

    public UserDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        try {
          
            TypedQuery<User> query = entityManager.createQuery(HQL_FIND_USER_BY_USERNAME, User.class);
            query.setParameter("username", username);

            User user = query.getSingleResult();
            return Optional.of(user);

        } catch (NoResultException e) {
            return Optional.empty();
        }
    }


    public void update(User user) {
        try {
            entityManager.merge(user);
        } catch (Exception e) {
            LOGGER.error("Failed to update trainee", e);
        }
    }

    public Optional<User> create(User user) {
        try{
            entityManager.persist(user);

            return Optional.of(user);
        } catch (Exception e) {
            LOGGER.error("Failed to create trainee", e);
            return Optional.empty();
        }
    }
}
