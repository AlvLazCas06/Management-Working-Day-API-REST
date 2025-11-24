package com.salesianostriana.dam.workingday.dto;

import com.salesianostriana.dam.workingday.model.Employee;
import com.salesianostriana.dam.workingday.model.Type;

import java.time.LocalDateTime;

public record CreateSigningCmd(
         LocalDateTime moment,
         Type type,
         Employee employee
) {
}
