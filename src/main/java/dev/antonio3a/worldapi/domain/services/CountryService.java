package dev.antonio3a.worldapi.domain.services;

import dev.antonio3a.worldapi.api.controllers.CountryController;
import dev.antonio3a.worldapi.domain.entities.Country;
import dev.antonio3a.worldapi.domain.repositories.CountryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
@RequiredArgsConstructor
public class CountryService {

    private final CountryRepository countryRepository;

    public Country getCountryByCode(String code) {
        return countryRepository.findById(code).orElseThrow()
                .add(linkTo(methodOn(CountryController.class).getCountryByCode(code)).withSelfRel());
    }

    public Page<Country> getCountries(Pageable pageable) {
        return countryRepository.findAll(pageable);
    }
}
