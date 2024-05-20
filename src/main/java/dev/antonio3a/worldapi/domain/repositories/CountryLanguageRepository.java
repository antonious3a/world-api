package dev.antonio3a.worldapi.domain.repositories;

import dev.antonio3a.worldapi.domain.entities.CountryLanguage;
import dev.antonio3a.worldapi.domain.entities.CountryLanguageId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryLanguageRepository extends JpaRepository<CountryLanguage, CountryLanguageId> {
}