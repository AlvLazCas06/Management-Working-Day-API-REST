package com.salesianostriana.dam.workingday.dto;

import java.math.BigDecimal;

public record CreateDepartmentCmd(
         String name,
         BigDecimal budget
) {
}
