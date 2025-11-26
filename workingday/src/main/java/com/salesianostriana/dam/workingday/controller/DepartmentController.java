package com.salesianostriana.dam.workingday.controller;

import com.salesianostriana.dam.workingday.dto.CreateDepartmentCmd;
import com.salesianostriana.dam.workingday.dto.DepartmentResponse;
import com.salesianostriana.dam.workingday.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/department")
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;

    @GetMapping
    public List<DepartmentResponse> getAll() {
        return departmentService.findAll().stream()
                .map(DepartmentResponse::of)
                .toList();
    }

    @PostMapping
    public ResponseEntity<DepartmentResponse> createDepartment(
            @RequestBody CreateDepartmentCmd cmd
            ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(DepartmentResponse.of(departmentService.save(cmd)));
    }

    @GetMapping("/{id:[0-9]+}")
    public DepartmentResponse getById(
            @PathVariable Long id
    ) {
        return DepartmentResponse.of(departmentService.findById(id));
    }

    @PutMapping("/{id:[0-9]+}")
    public ResponseEntity<DepartmentResponse> editDepartment(
            @PathVariable Long id,
            @RequestBody CreateDepartmentCmd cmd
    ) {
        return ResponseEntity.ok(DepartmentResponse.of(departmentService.edit(id, cmd)));
    }

    @DeleteMapping("/{id:[0-9]+}")
    public ResponseEntity<?> deleteDepartment(
            @PathVariable Long id
    ) {
        departmentService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
