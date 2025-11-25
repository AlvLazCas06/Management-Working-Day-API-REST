package com.salesianostriana.dam.workingday.dto;

import com.salesianostriana.dam.workingday.model.Department;

import java.math.BigDecimal;

public record CreateDepartmentCmd(
         String name,
         BigDecimal budget
) {

    public static Department toEntity(CreateDepartmentCmd cmd) {
        return Department.builder()
                .name(cmd.name)
                .budget(cmd.budget)
                .build();
    }

}
