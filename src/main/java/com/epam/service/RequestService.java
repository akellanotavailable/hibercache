package com.epam.service;

import com.epam.dto.RequestDto;

import java.util.List;

/**
 * CRUD service for Request entity.
 */
public interface RequestService {
    RequestDto create(RequestDto dto);

    RequestDto get(String uuid);

    List<RequestDto> getByUserId(String userUuid);

    RequestDto update(RequestDto dto);

    void delete(String id);
}
