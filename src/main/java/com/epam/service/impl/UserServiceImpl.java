package com.epam.service.impl;

import com.epam.dao.UserDao;
import com.epam.dto.UserDto;
import com.epam.entity.Request;
import com.epam.entity.User;
import com.epam.service.UserAssembler;
import com.epam.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserAssembler assembler;

    @Override
    public UserDto create(UserDto dto) {
        User user = assembler.assemble(dto);
        userDao.create(user);
        return assembler.assemble(user);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDto get(String name) {
        User user = userDao.read(name);
        return assembler.assemble(user);
    }

    @Override
    @Transactional
    public UserDto update(UserDto dto) {
        User user = userDao.readById(dto.getId());
        User updatedUser = assembler.assemble(dto);

        performUpdate(user, updatedUser);

        return assembler.assemble(user);
    }

    private void performUpdate(User persistentUser, User newUser) {
        persistentUser.setName(newUser.getName());
        persistentUser.setRole(newUser.getRole());

        updateRequests(persistentUser.getRequests(), newUser.getRequests());
    }

    private void updateRequests(List<Request> persistentRequests, List<Request> newRequests) {
        Map<String, Request> currentRequests = newRequests
                .stream()
                .filter(x -> Objects.nonNull(x.getId()))
                .collect(Collectors.toMap(Request::getId, Function.identity()));

        List<Request> requestsToAdd = newRequests
                .stream()
                .filter(x -> Objects.isNull(x.getId()))
                .collect(Collectors.toList());

        Iterator<Request> persistentIterator = persistentRequests.iterator();
        while (persistentIterator.hasNext()) {
            Request persistentRequest = persistentIterator.next();
            if (currentRequests.containsKey(persistentRequest.getId())) {
                Request updatedRequest = currentRequests.get(persistentRequest.getId());
                updateRequests(persistentRequest, updatedRequest);
            } else {
                persistentIterator.remove();
                persistentRequest.setUser(null);
            }
        }
        persistentRequests.addAll(requestsToAdd);
    }

    private void updateRequests(Request persistentRequest, Request updatedRequest) {
        persistentRequest.setName(updatedRequest.getName());
        persistentRequest.setCost(updatedRequest.getCost());
    }

    @Override
    @Transactional
    public void delete(String id) {
        userDao.delete(id);
    }
}
