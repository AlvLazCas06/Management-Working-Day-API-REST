package com.salesianostriana.dam.workingday.exception;

public class SigningNotFoundException extends NotFoundException {
    public SigningNotFoundException(String message) {
        super(message);
    }

    public SigningNotFoundException() {
        super("No existen fichajes por parte de este empleado");
    }

}
