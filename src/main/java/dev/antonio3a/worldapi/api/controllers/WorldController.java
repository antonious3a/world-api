package dev.antonio3a.worldapi.api.controllers;

import dev.antonio3a.worldapi.api.payloads.CityDto;
import dev.antonio3a.worldapi.api.payloads.CountryDto;
import dev.antonio3a.worldapi.domain.services.CityService;
import dev.antonio3a.worldapi.domain.services.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class WorldController {

    private final CountryService countryService;

    private final CityService cityService;

    @QueryMapping
    public CountryDto getCountry(@Argument String code) {
        return countryService.getCountryByCode(code);
    }

    @QueryMapping
    public CityDto getCity(@Argument Integer id) {
        return cityService.getCityById(id);
    }
}
