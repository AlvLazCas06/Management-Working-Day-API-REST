package com.salesianostriana.dam.workingday.dto;

import com.salesianostriana.dam.workingday.model.Employee;

import java.math.BigDecimal;

public record EditEmployeeCmd(
        String fullName,
        String position,
        BigDecimal salary
) {

    public static Employee toEntity(EditEmployeeCmd cmd) {
        return Employee.builder()
                .fullName(cmd.fullName)
                .position(cmd.position)
                .salary(cmd.salary)
                .build();
    }

}
