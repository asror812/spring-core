package com.example.demo.dao;

import com.example.demo.model.Trainer;
import com.example.demo.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public class TrainerDAOImpl implements TrainerDAO {

    private static final String HQL_FIND_TRAINER_BY_USER_ID = "FROM Trainer T WHERE T.user.id = :userId";
    private static final String HQL_GET_ALL_TRAINERS = "from Trainer";


    @PersistenceContext
    private EntityManager entityManager;
    
    private final UserDAO userDAO;

    public TrainerDAOImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(TrainerDAOImpl.class);

    @Override
    public Optional<Trainer> create(Trainer trainer) {
        try {

            User user = trainer.getUser();
            entityManager.persist(user);


            entityManager.persist(trainer);

            return Optional.of(trainer);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Trainer> findById(UUID id) {
        Trainer trainer = entityManager.find(Trainer.class, id);

        if (trainer == null) {
            return Optional.empty();
        }

        return Optional.of(trainer);
    }

    @Override
    public Optional<Trainer> findByUsername(String username) {
        Optional<User> existingUser = userDAO.findByUsername(username);

        if (existingUser.isEmpty()) {
            throw new EntityNotFoundException("User with username " + username + " not found");
        }

        try {
            TypedQuery<Trainer> query = entityManager.createQuery(HQL_FIND_TRAINER_BY_USER_ID, Trainer.class);
            query.setParameter("userId", existingUser.get().getId());

            Trainer trainer = query.getSingleResult();

            return Optional.of(trainer);

        } catch (NoResultException e) {
            LOGGER.error("No Trainer found with username {}", username);
            return Optional.empty();
        }
    }

    @Override
    public void update(Trainer trainer) {
        try {
            entityManager.merge(trainer);
        } catch (Exception e) {
            LOGGER.error("Failed to update trainer", e);
        }
    }

    public List<Trainer> getAll() {
       
        TypedQuery<Trainer> query = entityManager.createQuery(HQL_GET_ALL_TRAINERS, Trainer.class);

        List<Trainer> results = query.getResultList();

        return results;
    }

}
