package com.epam.service.impl;

import com.epam.dao.RequestDao;
import com.epam.dto.RequestDto;
import com.epam.entity.Request;
import com.epam.service.RequestAssembler;
import com.epam.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class RequestServiceImpl implements RequestService {

    @Autowired
    private RequestDao requestDao;

    @Autowired
    private RequestAssembler requestAssembler;

    @Override
    public RequestDto create(RequestDto dto) {
        Request request = requestAssembler.assemble(dto);
        requestDao.save(request);
        return requestAssembler.assemble(request);
    }

    @Override
    @Transactional(readOnly = true)
    public RequestDto get(String uuid) {
        Request request = requestDao.getById(uuid);
        return requestAssembler.assemble(request);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RequestDto> getByUserId(String userUuid) {

        List<Request> byUserId = requestDao.getByUserId(userUuid);

        List<RequestDto> collect = new ArrayList<>();

        for (Request request : byUserId) {
            RequestDto assemble = requestAssembler.assemble(request);
            collect.add(assemble);
        }
        return collect;
    }

    @Override
    @Transactional
    public RequestDto update(RequestDto dto) {
        Request request = requestDao.getById(dto.getId());
        Request updatedRequest = requestAssembler.assemble(dto);

        request.setName(updatedRequest.getName());
        request.setCost(updatedRequest.getCost());
        request.setUser(updatedRequest.getUser());

        return requestAssembler.assemble(request);
    }

    @Override
    public void delete(String id) {
        requestDao.delete(id);
    }
}
