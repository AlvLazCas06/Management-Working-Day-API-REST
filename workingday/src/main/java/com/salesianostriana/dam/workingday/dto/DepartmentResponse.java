package com.salesianostriana.dam.workingday.dto;

import com.salesianostriana.dam.workingday.model.Department;

import java.math.BigDecimal;
import java.util.List;

public record DepartmentResponse(
        Long id,
        String name,
        BigDecimal budget,
        List<EmployeeResponse> employees
) {

    public static DepartmentResponse of(Department department) {
        return new DepartmentResponse(
                department.getId(),
                department.getName(),
                department.getBudget(),
                department.getEmployees().stream()
                        .map(EmployeeResponse::of)
                        .toList()
        );
    }

}
