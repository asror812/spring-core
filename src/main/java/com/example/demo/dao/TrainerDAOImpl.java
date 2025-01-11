package com.example.demo.dao;

import com.example.demo.exceptions.DataAccessException;
import com.example.demo.model.Trainer;
import com.example.demo.model.User;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public class TrainerDAOImpl extends AbstractHibernateDAO<Trainer> implements TrainerDAO {

    private static final String HQL_FIND_TRAINER_BY_USER_ID = "FROM Trainer T WHERE T.user.id = :userId";
    private static final String HQL_GET_ALL = "FROM Trainer";
    private static final Logger LOGGER = LoggerFactory.getLogger(TrainerDAOImpl.class);
    private final UserDAO userDAO;

    public TrainerDAOImpl(UserDAO userDAO) {
        super(Trainer.class);
        this.userDAO = userDAO;
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
            LOGGER.warn("No Trainer found with username {}", username);
            return Optional.empty();
        }
    }

    @Override
    public List<Trainer> getAll() {
        try {
            TypedQuery<Trainer> query = entityManager.createQuery(HQL_GET_ALL, Trainer.class);
            return query.getResultList();
        } catch (NoResultException e) {
            LOGGER.error("Failed to get trainers");
            throw new DataAccessException("Failed to get trainers");
        }
    }

}
