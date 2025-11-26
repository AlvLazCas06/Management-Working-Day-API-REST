package com.salesianostriana.dam.workingday.exception;

public class SigningDuplicateException extends NotFoundException {
    public SigningDuplicateException(String message) {
        super(message);
    }

    public SigningDuplicateException() {
        super("No puedes fichar del mismo tipo dos veces");
    }

}
