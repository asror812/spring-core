package com.example.demo.dao;

import java.util.Optional;

import org.springframework.stereotype.Repository;
import com.example.demo.model.User;
import jakarta.persistence.TypedQuery;

@Repository
public class UserDAOImpl extends AbstractHibernateDAO<User> implements UserDAO {

    private static final String HQL_FIND_USER_BY_USERNAME = "FROM User WHERE username = :username";
    private static final String HQL_FIND_USER_BY_USERNAME_AND_PASSWORD = "FROM User WHERE username = :username AND password = :password";

    public UserDAOImpl() {
        super(User.class);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        TypedQuery<User> query = entityManager.createQuery(HQL_FIND_USER_BY_USERNAME, User.class);
        query.setParameter("username", username);
        return Optional.ofNullable(query.getSingleResult());
    }

    @Override
    public Optional<User> findByUsernameAndPassword(String username, String password) {
        TypedQuery<User> query = entityManager.createQuery(HQL_FIND_USER_BY_USERNAME_AND_PASSWORD, User.class);
        query.setParameter("username", username);
        query.setParameter("password", password);
        return Optional.ofNullable(query.getSingleResult());
    }

}
