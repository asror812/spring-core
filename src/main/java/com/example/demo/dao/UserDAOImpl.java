package com.example.demo.dao;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import com.example.demo.dto.UserAuthDTO;
import com.example.demo.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Repository
public class UserDAOImpl implements UserDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserDAOImpl.class);
    
   
    @PersistenceContext
    private EntityManager entityManager;

    public UserDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        try {
            String hql = "FROM User U WHERE U.username = :username";
            TypedQuery<User> query = entityManager.createQuery(hql, User.class);
            query.setParameter("username", username);

            User user = query.getSingleResult();
            return Optional.of(user);

        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    public Optional<User> findByUsernameAndPassword(UserAuthDTO authDTO) {

        try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();

            CriteriaQuery<User> cq = cb.createQuery(User.class);
            Root<User> root = cq.from(User.class);

            Predicate predicateForUsername = cb.equal(root.get("username"), authDTO.getUsername());
            Predicate predicateForPassword = cb.equal(root.get("password"), authDTO.getPassword());

            Predicate finalPredicate = cb.and(predicateForUsername, predicateForPassword);

            cq.where(finalPredicate);

            User user = entityManager.createQuery(cq).getSingleResult();

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
