package com.salesianostriana.dam.workingday.dto;

import com.salesianostriana.dam.workingday.model.Employee;

import java.math.BigDecimal;

public record CreateEmployeeCmd(
        String fullName,
        String position,
        BigDecimal salary,
        Long departmentId
) {

    public static Employee toEntity(CreateEmployeeCmd cmd) {
        return Employee.builder()
                .fullName(cmd.fullName)
                .position(cmd.position)
                .salary(cmd.salary)
                .build();
    }

}
