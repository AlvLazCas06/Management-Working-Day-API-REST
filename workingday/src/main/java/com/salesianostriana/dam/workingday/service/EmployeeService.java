package com.salesianostriana.dam.workingday.service;

import com.salesianostriana.dam.workingday.dto.CreateEmployeeCmd;
import com.salesianostriana.dam.workingday.dto.CreateSigningCmd;
import com.salesianostriana.dam.workingday.model.Employee;
import com.salesianostriana.dam.workingday.model.Signing;
import com.salesianostriana.dam.workingday.repository.EmployeeRepository;
import com.salesianostriana.dam.workingday.repository.SigningRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final SigningRepository signingRepository;

    public Employee save(CreateEmployeeCmd cmd) {
        Employee employee = CreateEmployeeCmd.toEntity(cmd);
        return employeeRepository.save(employee);
    }

    public List<Employee> findAll() {
        List<Employee> result = employeeRepository.findAll();
        if (result.isEmpty()) {
            throw new EntityNotFoundException("No existen empleados en la base de datos");
        }
        return result;
    }

    public Employee findById(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("El empleado con el id: %d, no existe.".formatted(id)));
        return employee;
    }

    public Employee edit(Long id, CreateEmployeeCmd cmd) {
        Employee employee = CreateEmployeeCmd.toEntity(cmd);
        if (employeeRepository.findById(id).isEmpty()) {
            throw new EntityNotFoundException("El empleado con el id: %d, no existe.".formatted(id));
        }
        employee.setId(id);
        return employee;
    }

    public Signing setSigning(Long id, CreateSigningCmd cmd) {
        Signing signing = CreateSigningCmd.toEntity(cmd);
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("El empleado con el id: %d, no existe.".formatted(id)));
        if (employee.getSignings().getLast().getType() == signing.getType()) {
            throw new RuntimeException();
        }
        signing.setEmployee(employee);
        employee.getSignings().add(signingRepository.save(signing));
        return signing;
    }

    public List<Signing> listSigning(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("El empleado con el id: %d, no existe.".formatted(id)));
        List<Signing> signings = employee.getSignings();
        if (signings.isEmpty() && signings == null) {
            throw new EntityNotFoundException("No existen fichaje en este empleado.");
        }
        return signings;
    }

    public void delete(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("El empleado con el id: %d, no existe".formatted(id)));
        employee.getSignings().clear();
        employeeRepository.delete(employee);
    }

}
