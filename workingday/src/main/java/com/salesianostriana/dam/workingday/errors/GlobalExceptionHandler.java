package com.salesianostriana.dam.workingday.errors;

import com.salesianostriana.dam.workingday.exception.BudgetExceededException;
import com.salesianostriana.dam.workingday.exception.EmployeeNotFoundException;
import com.salesianostriana.dam.workingday.exception.IllegalArgumentException;
import com.salesianostriana.dam.workingday.exception.NotFoundException;
import com.salesianostriana.dam.workingday.exception.SigningDuplicateException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.URI;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ProblemDetail handleNotFoundException(NotFoundException ex) {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        pd.setTitle("Entidad no encontrada");
        pd.setType(URI.create("https://dam.salesianos-triana.com/entity-not-found"));
        return pd;
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(SigningDuplicateException.class)
    public ProblemDetail handleSigningDuplicateException(SigningDuplicateException ex) {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, ex.getMessage());
        pd.setTitle("Fichaje duplicado");
        pd.setType(URI.create("https://dam.salesianos-triana.com/duplicate-signing"));
        return pd;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BudgetExceededException.class)
    public ProblemDetail handleBudgetExceededException(BudgetExceededException ex) {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
        pd.setTitle("Presupuesto excedido");
        pd.setType(URI.create("https://dam.salesianos-triana.com/exceeded-budget"));
        return pd;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ProblemDetail handleBudgetExceededException(IllegalArgumentException ex) {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
        pd.setTitle("Error de datos");
        pd.setType(URI.create("https://dam.salesianos-triana.com/data-error"));
        return pd;
    }

}
