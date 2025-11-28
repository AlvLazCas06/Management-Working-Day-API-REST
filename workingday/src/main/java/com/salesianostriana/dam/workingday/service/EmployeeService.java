package com.salesianostriana.dam.workingday.service;

import com.salesianostriana.dam.workingday.dto.CreateEmployeeCmd;
import com.salesianostriana.dam.workingday.exception.*;
import com.salesianostriana.dam.workingday.exception.IllegalArgumentException;
import com.salesianostriana.dam.workingday.model.Department;
import com.salesianostriana.dam.workingday.model.Employee;
import com.salesianostriana.dam.workingday.model.Signing;
import com.salesianostriana.dam.workingday.model.Type;
import com.salesianostriana.dam.workingday.repository.EmployeeRepository;
import com.salesianostriana.dam.workingday.repository.SigningRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final DepartmentService departmentService;
    private final EmployeeRepository employeeRepository;
    private final SigningRepository signingRepository;

    public Employee save(CreateEmployeeCmd cmd) {
        Employee employee = CreateEmployeeCmd.toEntity(cmd);
        Department department;
        double total;
        if (!StringUtils.hasText(employee.getFullName())
                || !StringUtils.hasText(employee.getPosition())
                || employee.getSalary() == null
                || employee.getSalary().equals(0)
        ) {
            throw new IllegalArgumentException();
        }
        if (cmd.departmentId() != null) {
            try {
                department = departmentService.findById(cmd.departmentId());
                department.getEmployees().add(employee);
                employee.setDepartment(department);
                total = department.getEmployees().stream()
                        .mapToDouble(e -> e.getSalary().doubleValue())
                        .sum();
                if (department.getBudget().doubleValue() < total) {
                    throw new BudgetExceededException();
                }
            } catch (DepartmentNotFoundException ex) {
                throw new IllegalArgumentException();
            }
        }
        return employeeRepository.save(employee);
    }

    public List<Employee> findAll() {
        List<Employee> result = employeeRepository.findAll();
        if (result.isEmpty()) {
            throw new EmployeeNotFoundException();
        }
        return result;
    }

    public Employee findById(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));
        return employee;
    }

    public Employee edit(Long employeeId, CreateEmployeeCmd cmd, Long departmentId) {
        Employee employee = CreateEmployeeCmd.toEntity(cmd);
        Department department;
        double total;
        employee.setId(employeeId);
        if (employeeRepository.findById(employeeId).isEmpty()) {
            throw new EmployeeNotFoundException(employeeId);
        }
        try {
            department = departmentService.findById(departmentId);
            if (!department.getEmployees().contains(employee)) {
                department.getEmployees().add(employee);
            }
            employee.setDepartment(department);
            total = department.getEmployees().stream()
                    .mapToDouble(e -> e.getSalary().doubleValue())
                    .sum();
            if (department.getBudget().doubleValue() < total) {
                throw new BudgetExceededException();
            }
        } catch (DepartmentNotFoundException ex) {
            throw new IllegalArgumentException();
        }
        if (!findById(employeeId).getSignings().isEmpty()) {
            employee.setSignings(findById(employeeId).getSignings());
        }
        return employeeRepository.save(employee);
    }

    public Signing setSigning(Long id) {
        Signing signing;
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));
        if (!employee.getSignings().isEmpty()
                && employee.getSignings().getLast().getType() == Type.ENTRY) {
            signing = Signing.builder().type(Type.EXIT).build();
        } else if (!employee.getSignings().isEmpty()
                && employee.getSignings().getLast().getType() == Type.EXIT) {
            signing = Signing.builder().type(Type.ENTRY).build();
        } else {
            signing = Signing.builder().type(Type.ENTRY).build();
        }
        if (!employee.getSignings().isEmpty()
                && employee.getSignings().getLast().getType() == signing.getType()) {
            throw new SigningDuplicateException();
        }
        signing.setEmployee(employee);
        return signingRepository.save(signing);
    }

    public List<Signing> listSigning(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));
        List<Signing> signings = employee.getSignings();
        if (signings.isEmpty()) {
            throw new SigningNotFoundException();
        }
        return signings;
    }

    public void delete(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));
        if (!employee.getSignings().isEmpty()) {
            employee.getSignings()
                    .forEach(signingRepository::delete);
        }
        employeeRepository.delete(employee);
    }

}
