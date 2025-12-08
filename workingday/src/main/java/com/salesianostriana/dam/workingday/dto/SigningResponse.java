package com.salesianostriana.dam.workingday.dto;

import com.salesianostriana.dam.workingday.model.Signing;
import com.salesianostriana.dam.workingday.model.Type;

import java.time.LocalDateTime;

public record SigningResponse(
        LocalDateTime moment,
        Type type
) {

    public static SigningResponse of(Signing signing) {
        return new SigningResponse(
                signing.getMoment(),
                signing.getType()
        );
    }

}
