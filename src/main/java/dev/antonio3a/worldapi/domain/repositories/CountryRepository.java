package dev.antonio3a.worldapi.domain.repositories;

import dev.antonio3a.worldapi.domain.entities.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country, String> {
}