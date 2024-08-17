package dev.antonio3a.worldapi.domain.services;

import dev.antonio3a.worldapi.api.controllers.CountryController;
import dev.antonio3a.worldapi.api.payloads.CountryDto;
import dev.antonio3a.worldapi.domain.repositories.CountryRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
@RequiredArgsConstructor
public class CountryService {

    private final CountryRepository countryRepository;

    private final ModelMapper modelMapper;

    public CountryDto getCountryByCode(String code) {
        return modelMapper.map(countryRepository.findById(code).orElseThrow(), CountryDto.class)
                .add(linkTo(methodOn(CountryController.class).getCountryByCode(code)).withSelfRel());
    }

    public PagedModel<CountryDto> getCountries(Pageable pageable,
                                               PagedResourcesAssembler assembler) {
        return assembler.toModel(countryRepository.findAll(pageable).map(country -> modelMapper.map(country, CountryDto.class)));
    }
}
