package com.salesianostriana.dam.workingday.exception;

public class DepartmentNotFoundException extends NotFoundException {
    public DepartmentNotFoundException(String message) {
        super(message);
    }

    public DepartmentNotFoundException(Long id) {
        super("El departamento con el id: %d, no existe.".formatted(id));
    }

    public DepartmentNotFoundException() {
        super("No existen empleados en la base de datos");
    }
}
