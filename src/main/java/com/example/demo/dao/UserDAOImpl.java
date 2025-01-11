package com.example.demo.dao;

import java.util.Optional;

import org.springframework.stereotype.Repository;
import com.example.demo.model.User;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;


@Repository
public class UserDAOImpl extends AbstractHibernateDAO<User> implements UserDAO {

    private static final String HQL_FIND_USER_BY_USERNAME = "FROM User U WHERE U.username = :username";

    public UserDAOImpl() {
        super(User.class);
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


}
