package com.mutasem.event.finder.DAO;

import com.mutasem.event.finder.models.User;
import com.mutasem.event.finder.models.WebUser;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class UserDao implements IUserDao{
    private EntityManager entityManager;

    public UserDao(EntityManager theEntityManager) {
        this.entityManager = theEntityManager;
    }

    @Override
    public User findByEmail(String email) {

        // retrieve/read from database using username
        TypedQuery<User> theQuery = entityManager.createQuery("from User where email=:email", User.class);
        theQuery.setParameter("email", email);

        User user = null;
        try {
            user = theQuery.getSingleResult();
        } catch (Exception e) {
            user = null;
        }

        return user;
    }

    @Transactional
    @Override
    public void save(User user) {
        entityManager.persist(user);
    }

    @Transactional
    @Override
    public void updateUser(User user) {
        entityManager.merge(user);
    }

}
