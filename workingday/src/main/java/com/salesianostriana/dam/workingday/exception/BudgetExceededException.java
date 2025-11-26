package com.salesianostriana.dam.workingday.exception;

public class BudgetExceededException extends RuntimeException {
    public BudgetExceededException(String message) {
        super(message);
    }

    public BudgetExceededException() {
        super("El presupuesto del departamento se ha excedido");
    }

}
