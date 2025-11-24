package com.salesianostriana.dam.workingday.dto;

import java.math.BigDecimal;

public record DepartmentResponse(
        Long id,
        String name,
        BigDecimal budget
) {
}
