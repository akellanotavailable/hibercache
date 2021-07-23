package com.epam.service;

import com.epam.dto.RequestDto;
import com.epam.entity.Request;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;

/**
 * Converts RequestDTO into Request entity and vice versa.
 */
public interface RequestAssembler {
    RequestDto assemble(Request request);

    Request assemble(RequestDto requestDto);
}
