package com.epam.service.impl;

import com.epam.dao.UserDao;
import com.epam.dto.RequestDto;
import com.epam.entity.Request;
import com.epam.service.RequestAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RequestAssemblerImpl implements RequestAssembler {

    @Autowired
    private UserDao userDao;

    @Override
    public RequestDto assemble(Request request) {
        RequestDto dto = new RequestDto();
        dto.setId(request.getId());
        dto.setName(request.getName());
        dto.setCost(request.getCost());
        dto.setUserId(request.getUser().getId());
        return dto;
    }

    @Override
    public Request assemble(RequestDto requestDto) {
        Request request = new Request();
        request.setId(requestDto.getId());
        request.setCost(requestDto.getCost());
        request.setName(requestDto.getName());
        request.setUser(userDao.readById(requestDto.getUserId()));
        return request;
    }
}
