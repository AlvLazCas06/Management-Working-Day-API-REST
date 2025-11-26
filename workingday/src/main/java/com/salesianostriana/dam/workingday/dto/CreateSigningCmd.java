package com.salesianostriana.dam.workingday.dto;

import com.salesianostriana.dam.workingday.model.Signing;
import com.salesianostriana.dam.workingday.model.Type;

public record CreateSigningCmd(
        String type
) {

    public static Signing toEntity(CreateSigningCmd cmd) {
        return Signing.builder()
                .type(Type.valueOf(cmd.type))
                .build();
    }

}
