package com.salesianostriana.dam.workingday.repository;

import com.salesianostriana.dam.workingday.model.Signing;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SigningRepository
        extends JpaRepository<Signing, Long> {
}
