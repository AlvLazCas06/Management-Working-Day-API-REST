package com.salesianostriana.dam.workingday.repository;

import com.salesianostriana.dam.workingday.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository
        extends JpaRepository<Employee, Long> {
}
