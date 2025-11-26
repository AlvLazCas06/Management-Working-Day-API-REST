package com.salesianostriana.dam.workingday.exception;

public class IllegalArgumentException extends RuntimeException {
    public IllegalArgumentException(String message) {
        super(message);
    }

    public IllegalArgumentException() {
        super("Error al crear/editar la entidad");
    }

}
