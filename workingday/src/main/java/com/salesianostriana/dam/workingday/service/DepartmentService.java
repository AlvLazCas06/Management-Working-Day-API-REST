package com.salesianostriana.dam.workingday.service;

import com.salesianostriana.dam.workingday.dto.CreateDepartmentCmd;
import com.salesianostriana.dam.workingday.model.Department;
import com.salesianostriana.dam.workingday.repository.DepartmentRepository;
import jakarta.persistence.EntityNotFoundException;
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
        return result;
    }

    public Department findById(Long id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("El departamento con el id: %d, no existe".formatted(id)));
        return department;
    }

    public Department edit(Long id, CreateDepartmentCmd cmd) {
        Department department = CreateDepartmentCmd.toEntity(cmd);
        if (departmentRepository.findById(id).isEmpty()) {
            throw new EntityNotFoundException("El departamento con el id: %d, no existe".formatted(id));
        }
        department.setId(id);
        return department;
    }

}
