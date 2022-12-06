package com.zhangjgux.easygarage.dao;

import com.zhangjgux.easygarage.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository{

    private EntityManager entityManager;

    @Autowired
    public UserRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public User getUserByEmail(String email) {
        TypedQuery<User> theQuery =
                entityManager.createQuery("SELECT u FROM User u WHERE u.email = '" + email + "'", User.class);
        try {
            return theQuery.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public User getCurrent() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        TypedQuery<User> theQuery =
                entityManager.createQuery("SELECT u FROM User u WHERE u.email = '" + email + "'", User.class);
        try {
            return theQuery.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<User> findAll() {
        TypedQuery<User> theQuery =
                entityManager.createQuery("SELECT u FROM User u", User.class);
        return theQuery.getResultList();
    }

    @Override
    public User findById(int id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public void save(User user) {
        User u = entityManager.merge(user);
        user.setId(u.getId());
    }

    @Override
    public void deleteById(int id) {
        TypedQuery<User> theQuery =
                entityManager.createQuery("DELETE FROM User u WHERE u.id = " + id, User.class);
        theQuery.executeUpdate();
    }
}
