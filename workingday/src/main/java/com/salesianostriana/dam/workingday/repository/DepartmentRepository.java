package com.salesianostriana.dam.workingday.repository;

import com.salesianostriana.dam.workingday.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository
        extends JpaRepository<Department, Long> {

    boolean existsDepartmentByName(String name);

}
