package dev.antonio3a.worldapi.domain.repositories;

import dev.antonio3a.worldapi.domain.entities.City;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRepository extends JpaRepository<City, Integer> {
}