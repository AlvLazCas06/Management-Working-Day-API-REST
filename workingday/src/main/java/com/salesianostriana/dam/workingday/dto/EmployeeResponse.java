package com.salesianostriana.dam.workingday.dto;

import com.salesianostriana.dam.workingday.model.Employee;

import java.math.BigDecimal;
import java.util.List;

public record EmployeeResponse(
        Long id,
        String fullName,
        String position,
        BigDecimal salary,
        String departmentName,
        List<SigningResponse> signings
) {

    public static EmployeeResponse of(Employee employee) {
        return new EmployeeResponse(
                employee.getId(),
                employee.getFullName(),
                employee.getPosition(),
                employee.getSalary(),
                employee.getDepartment().getName(),
                employee.getSignings().stream()
                        .map(SigningResponse::of)
                        .toList()
        );
    }

}
