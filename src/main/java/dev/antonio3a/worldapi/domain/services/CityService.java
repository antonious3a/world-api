package dev.antonio3a.worldapi.domain.services;

import dev.antonio3a.worldapi.domain.entities.City;
import dev.antonio3a.worldapi.domain.repositories.CityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CityService {

    private final CityRepository cityRepository;

    public City getCityById(Integer id) {
        return cityRepository.findById(id).orElseThrow();
    }

    public Page<City> getCities(Integer page, Integer size, String sortDirection, String... sortBy) {
        return cityRepository.findAll(PageRequest.of(page, size, Sort.Direction.fromString(sortDirection), sortBy));
    }
}
