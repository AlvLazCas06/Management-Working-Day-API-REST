package com.salesianostriana.dam.workingday.dto;

import com.salesianostriana.dam.workingday.model.Employee;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
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
                employee.getDepartment() != null ? employee.getDepartment().getName() : "Aún no está en ningún departamento",
                employee.getSignings().stream()
                        .map(SigningResponse::of)
                        .toList()
        );
    }

}
