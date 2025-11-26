package com.salesianostriana.dam.workingday.service;

import com.salesianostriana.dam.workingday.dto.CreateDepartmentCmd;
import com.salesianostriana.dam.workingday.exception.BudgetExceededException;
import com.salesianostriana.dam.workingday.exception.DepartmentNotFoundException;
import com.salesianostriana.dam.workingday.model.Department;
import com.salesianostriana.dam.workingday.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    public Department save(CreateDepartmentCmd cmd) {
        Department department = CreateDepartmentCmd.toEntity(cmd);
        return departmentRepository.save(department);
    }

    public List<Department> findAll() {
        List<Department> result = departmentRepository.findAll();
        if (result.isEmpty()) {
            throw new DepartmentNotFoundException();
        }
        return result;
    }

    public Department findById(Long id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new DepartmentNotFoundException(id));
        return department;
    }

    public Department edit(Long id, CreateDepartmentCmd cmd) {
        Department department = CreateDepartmentCmd.toEntity(cmd);
        double total;
        if (departmentRepository.findById(id).isEmpty()) {
            throw new DepartmentNotFoundException(id);
        }
        if (!department.getEmployees().isEmpty()) {
            total = department.getEmployees().stream()
                    .mapToDouble(employee -> employee.getSalary().doubleValue())
                    .sum();
            if (total > department.getBudget().doubleValue()) {
                throw new BudgetExceededException();
            }
        }
        department.setId(id);
        return department;
    }

    public void delete(Long id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new DepartmentNotFoundException(id));
        department.getEmployees().clear();
        departmentRepository.delete(department);
    }

}
