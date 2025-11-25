package com.salesianostriana.dam.workingday.dto;

import com.salesianostriana.dam.workingday.model.Signing;
import com.salesianostriana.dam.workingday.model.Type;

import java.time.LocalDateTime;

public record CreateSigningCmd(
        LocalDateTime moment,
        Type type,
        Long employeeId
) {

    public static Signing toEntity(CreateSigningCmd cmd) {
        return Signing.builder()
                .moment(cmd.moment)
                .type(cmd.type)
                .build();
    }

}
