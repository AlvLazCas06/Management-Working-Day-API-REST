package com.salesianostriana.dam.workingday.controller;

import com.salesianostriana.dam.workingday.dto.CreateEmployeeCmd;
import com.salesianostriana.dam.workingday.dto.CreateSigningCmd;
import com.salesianostriana.dam.workingday.dto.EmployeeResponse;
import com.salesianostriana.dam.workingday.dto.SigningResponse;
import com.salesianostriana.dam.workingday.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/employee")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping
    public List<EmployeeResponse> listAll() {
        return employeeService.findAll()
                .stream()
                .map(EmployeeResponse::of)
                .toList();
    }

    @PostMapping
    public ResponseEntity<EmployeeResponse> createEmployee(
            @RequestBody CreateEmployeeCmd cmd
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(EmployeeResponse.of(employeeService.save(cmd)));
    }

    @GetMapping("/{id:[0-9]+}")
    public EmployeeResponse getById(@PathVariable Long id) {
        return EmployeeResponse.of(employeeService.findById(id));
    }

    @PutMapping("/{employeeId:[0-9]+}/department/{departmentId:[0-9]+}")
    public ResponseEntity<EmployeeResponse> editEmployee(
            @PathVariable Long employeeId,
            @PathVariable Long departmentId,
            @RequestBody CreateEmployeeCmd cmd
    ) {
        return ResponseEntity.ok(EmployeeResponse.of(employeeService.edit(employeeId, cmd)));
    }

    @PostMapping("/{id:[0-9]+}/signing")
    public ResponseEntity<SigningResponse> createSigning(
            @PathVariable Long id,
            @RequestBody CreateSigningCmd cmd
    ) {
       return ResponseEntity.status(HttpStatus.CREATED)
               .body(SigningResponse.of(employeeService.setSigning(id, cmd)));
    }

    @GetMapping("/{id:[0-9]+}/signing")
    public List<SigningResponse> getEmployeeSignings(@PathVariable Long id) {
        return employeeService.listSigning(id).stream()
                .map(SigningResponse::of)
                .toList();
    }

    @DeleteMapping("/{id:[0-9]+}")
    public ResponseEntity<?> deleteEmployee(@PathVariable Long id) {
        employeeService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
