package com.salesianostriana.dam.workingday.dto;

import java.math.BigDecimal;

public record CreateEmployeeCmd(
        String fullName,
        String position,
        BigDecimal salary,
        String departmentName
) {
}
