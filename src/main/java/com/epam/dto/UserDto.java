package com.epam.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserDto {

    private String id;

    private String name;

    private String role;

    private List<RequestDto> requests;
}
