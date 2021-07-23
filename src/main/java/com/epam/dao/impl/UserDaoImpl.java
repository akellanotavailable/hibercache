package com.epam.dao.impl;


import com.epam.dao.UserDao;
import com.epam.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Repository
@Transactional
public class UserDaoImpl implements UserDao {

    @Autowired
    private SessionFactory sessionFactory;

    public void create(User user) {
        Session session = sessionFactory.getCurrentSession();
        session.save(user);
    }

    public User readById(String uuid) {
        Session session = sessionFactory.getCurrentSession();
        User user = session.get(User.class, uuid);
        return Objects.requireNonNull(user, "User not found by id: " + uuid);
    }

    @Override
    public User read(String name) {
        Session session = sessionFactory.getCurrentSession();
        Object singleResult = session
                .getNamedQuery("userByName")
                .setParameter("userName", name)
                .getSingleResult();
        return (User) singleResult;
    }

    public void delete(String name) {
        User user = read(name);
        sessionFactory.getCurrentSession().delete(user);
    }
}
