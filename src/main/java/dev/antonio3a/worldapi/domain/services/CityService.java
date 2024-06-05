package dev.antonio3a.worldapi.domain.services;

import dev.antonio3a.worldapi.domain.entities.City;
import dev.antonio3a.worldapi.domain.repositories.CityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CityService {

    private final CityRepository cityRepository;

    public City getCityById(Integer id) {
        return cityRepository.findById(id).orElseThrow();
    }

    public Page<City> getCities(Pageable pageable) {
        return cityRepository.findAll(pageable);
    }
}
