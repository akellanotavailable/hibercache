package com.epam.service.impl;

import com.epam.dto.RequestDto;
import com.epam.dto.UserDto;
import com.epam.entity.Request;
import com.epam.entity.Role;
import com.epam.entity.User;
import com.epam.service.RequestAssembler;
import com.epam.service.UserAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserAssemblerImpl implements UserAssembler {

    @Autowired
    private RequestAssembler requestAssembler;

    @Override
    public User assemble(UserDto userDto) {
        User user = new User();
        user.setId(userDto.getId());
        user.setName(userDto.getName());
        user.setRole(Role.valueOf(userDto.getRole()));

        if (userDto.getRequests() != null){
            for (RequestDto requestDto : userDto.getRequests()) {
                Request request = requestAssembler.assemble(requestDto);
                request.setId(requestDto.getId());
                request.setName(requestDto.getName());
                request.setCost(requestDto.getCost());
                request.setUser(user);
                user.getRequests().add(request);
            }
        }
        return user;
    }

    @Override
    public UserDto assemble(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setRole(user.getRole().name());

        List<RequestDto> requests = user.getRequests()
                .stream()
                .map(request -> requestAssembler.assemble(request))
                .collect(Collectors.toList());

        userDto.setRequests(requests);
        return userDto;
    }
}
