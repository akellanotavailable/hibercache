package com.epam.dao;

import com.epam.entity.Request;

import java.util.List;

public interface RequestDao {
    void save(Request tourist);

    Request getById(String uuid);

    List<Request> getByUserId(String userUuid);

    void delete(String id);
}
