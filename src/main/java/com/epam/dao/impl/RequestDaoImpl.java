package com.epam.dao.impl;

import com.epam.dao.RequestDao;
import com.epam.entity.Request;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Repository
@Transactional
public class RequestDaoImpl implements RequestDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void save(Request tourist) {
        Session session = sessionFactory.getCurrentSession();
        session.save(tourist);
    }

    @Override
    public Request getById(String uuid) {
        Session session = sessionFactory.getCurrentSession();
        Request request = session.get(Request.class, uuid);
        return Objects.requireNonNull(request, "Request not found by id: " + uuid);
    }

    @Override
    public List<Request> getByUserId(String userUuid) {
        Session session = sessionFactory.getCurrentSession();
        return session
                .createNamedQuery("user", Request.class)
                .setParameter("userId", userUuid)
                .getResultList();
    }

    @Override
    public void delete(String id) {
        Request request = getById(id);
        sessionFactory.getCurrentSession().delete(request);
    }
}
