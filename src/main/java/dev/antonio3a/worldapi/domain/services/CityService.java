package dev.antonio3a.worldapi.domain.services;

import dev.antonio3a.worldapi.api.controllers.CityController;
import dev.antonio3a.worldapi.domain.entities.City;
import dev.antonio3a.worldapi.domain.repositories.CityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
@RequiredArgsConstructor
public class CityService {

    private final CityRepository cityRepository;

    public City getCityById(Integer id) {
        return cityRepository.findById(id).orElseThrow()
                .add(linkTo(methodOn(CityController.class).getCityById(id)).withSelfRel());
    }

    public Page<City> getCities(Pageable pageable) {
        return cityRepository.findAll(pageable);
    }
}
