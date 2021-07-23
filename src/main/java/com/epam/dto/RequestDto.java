package com.epam.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class RequestDto {

    private String id;

    private String name;

    private BigDecimal cost;

    private String userId;
}
