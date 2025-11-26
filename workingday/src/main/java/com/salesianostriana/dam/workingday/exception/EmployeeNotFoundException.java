package com.salesianostriana.dam.workingday.exception;

public class EmployeeNotFoundException extends NotFoundException {
    public EmployeeNotFoundException(String message) {
        super(message);
    }

    public EmployeeNotFoundException(Long id) {
        super("El empleado con el id: %d, no existe.".formatted(id));
    }

    public EmployeeNotFoundException() {
        super("No existen empleados en la base de datos");
    }
}
