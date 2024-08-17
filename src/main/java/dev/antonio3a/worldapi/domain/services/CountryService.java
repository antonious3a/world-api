package dev.antonio3a.worldapi.domain.services;

import dev.antonio3a.worldapi.api.controllers.CityController;
import dev.antonio3a.worldapi.api.controllers.CountryController;
import dev.antonio3a.worldapi.api.payloads.CityDto;
import dev.antonio3a.worldapi.api.payloads.CountryDto;
import dev.antonio3a.worldapi.domain.entities.Country;
import dev.antonio3a.worldapi.domain.repositories.CountryRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
@RequiredArgsConstructor
public class CountryService {

    private final CountryRepository countryRepository;

    private final ModelMapper modelMapper;

    public CountryDto getCountryByCode(String code) {
        return modelMapper.map(getCountry(code), CountryDto.class)
                .add(linkTo(methodOn(CountryController.class).getCountryByCode(code)).withSelfRel())
                .add(linkTo(methodOn(CountryController.class).getCitiesByCountryCode(code)).withRel("cities"))
                .add(linkTo(methodOn(CountryController.class).getCountries(Pageable.unpaged(), null)).withRel(IanaLinkRelations.COLLECTION));
    }

    public PagedModel<CountryDto> getCountries(Pageable pageable,
                                               PagedResourcesAssembler assembler) {
        return assembler.toModel(countryRepository.findAll(pageable)
                .map(country -> modelMapper.map(country, CountryDto.class)
                        .add(linkTo(methodOn(CountryController.class).getCountryByCode(country.getCode())).withSelfRel())
                        .add(linkTo(methodOn(CountryController.class).getCitiesByCountryCode(country.getCode())).withRel("cities"))
                )
        );
    }

    public List<CityDto> getCitiesByCountryCode(String code) {
        return getCountry(code).getCities().stream()
                .map(city -> modelMapper.map(city, CityDto.class)
                        .add(linkTo(methodOn(CityController.class).getCityById(city.getId())).withSelfRel())
                        .add(linkTo(methodOn(CityController.class).getCities(Pageable.unpaged(), null)).withRel(IanaLinkRelations.COLLECTION))
                        .add(linkTo(methodOn(CountryController.class).getCountryByCode(code)).withRel("country"))
                )
                .toList();
    }

    private Country getCountry(String code) {
        return countryRepository.findById(code).orElseThrow();
    }
}
