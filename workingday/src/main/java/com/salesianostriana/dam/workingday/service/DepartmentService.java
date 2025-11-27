package com.salesianostriana.dam.workingday.service;

import com.salesianostriana.dam.workingday.dto.CreateDepartmentCmd;
import com.salesianostriana.dam.workingday.exception.BudgetExceededException;
import com.salesianostriana.dam.workingday.exception.DepartmentNotFoundException;
import com.salesianostriana.dam.workingday.exception.IllegalArgumentException;
import com.salesianostriana.dam.workingday.model.Department;
import com.salesianostriana.dam.workingday.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    public Department save(CreateDepartmentCmd cmd) {
        Department department = CreateDepartmentCmd.toEntity(cmd);
        if (!StringUtils.hasText(department.getName())
                || department.getBudget() == null
                || departmentRepository.existsDepartmentByName(department.getName())) {
            throw new IllegalArgumentException();
        }
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
        department.setEmployees(findById(id).getEmployees());
        if (!StringUtils.hasText(department.getName())
                || department.getBudget() == null
                || departmentRepository.existsDepartmentByNameAndIdNot(department.getName(), id)) {
            throw new IllegalArgumentException();
        }
        if (!department.getEmployees().isEmpty()) {
            total = department.getEmployees().stream()
                    .mapToDouble(employee -> employee.getSalary().doubleValue())
                    .sum();
            if (department.getBudget().doubleValue() < total) {
                throw new BudgetExceededException();
            }
        }
        department.setId(id);
        return department;
    }

    public void delete(Long id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new DepartmentNotFoundException(id));
        if (!department.getEmployees().isEmpty()) {
            department.removeEmployee(
                    department.getEmployees()
                            .iterator()
                            .next()
            );
        }
        departmentRepository.delete(department);
    }

}
