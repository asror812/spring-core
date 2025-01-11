package com.example.demo.dao;

import com.example.demo.exceptions.DataAccessException;
import com.example.demo.model.Trainee;
import com.example.demo.model.User;

import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public class TraineeDAOImpl extends AbstractHibernateDAO<Trainee> implements TraineeDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(TraineeDAOImpl.class);
    private final UserDAO userDAO;
    private static final String FIND_TRAINEE_BY_USER_ID_QUERY = "FROM Trainee T WHERE T.user.id = :userId";

    public TraineeDAOImpl(UserDAO userDAO) {
        super(Trainee.class);
        this.userDAO = userDAO;
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
    public void delete(Trainee trainee) {
        try {
            entityManager.remove(trainee);
        } catch (Exception e) {
            LOGGER.error("Failed to delete {}", trainee);
            throw new DataAccessException("Failed to delete " + trainee);
        }
    }

}
