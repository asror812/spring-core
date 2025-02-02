package com.example.demo.dao;

import com.example.demo.model.Trainee;
import com.example.demo.model.User;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public class TraineeDAOImpl implements TraineeDAO {
    private static final Logger LOGGER = LoggerFactory.getLogger(TraineeDAOImpl.class);


    @PersistenceContext
    private EntityManager entityManager;

    private final UserDAO userDAO;

    private static final String FIND_TRAINEE_BY_USER_ID_QUERY = "FROM Trainee T WHERE T.user.id = :userId";


    private static final String HQL_GET_ALL_TRAINEES = "from Trainee";

    // Inject EntityManagerFactoryUtil
    public TraineeDAOImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    @Transactional
    public Optional<Trainee> create(Trainee trainee) {
        try {

            User user = trainee.getUser();

            entityManager.persist(user);

            entityManager.persist(trainee);
            return Optional.of(trainee);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public List<Trainee> getAll() {
        TypedQuery<Trainee> query = entityManager.createQuery(HQL_GET_ALL_TRAINEES, Trainee.class);

        List<Trainee> results = query.getResultList();

        return results;
    }

    @Override
    public Optional<Trainee> findById(UUID id) {
        Trainee trainee = entityManager.find(Trainee.class, id);
        
        if (trainee == null) {
            return Optional.empty();
        }
        
        return Optional.of(trainee);
    }

    @Override
    public Optional<Trainee> findByUsername(String username) {
        Optional<User> existingUser = userDAO.findByUsername(username);

        if (existingUser.isEmpty()) {
            throw new EntityNotFoundException("User with username " + username + " not found");
        }

        try {
            TypedQuery<Trainee> query = entityManager.createQuery(FIND_TRAINEE_BY_USER_ID_QUERY, Trainee.class);
            query.setParameter("userId", existingUser.get().getId());

            Trainee trainee = query.getSingleResult();
            return Optional.of(trainee);

        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public void update(Trainee trainee) {
        try {
            entityManager.merge(trainee);
        } catch (Exception e) {

            LOGGER.error("Failed to update trainee", e);
        }
    }

    @Override
    public void delete(Trainee trainee) {
        entityManager.remove(trainee);
    }

}
