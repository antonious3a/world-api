package dev.antonio3a.worldapi.api.controllers;

import dev.antonio3a.worldapi.domain.entities.Country;
import dev.antonio3a.worldapi.domain.services.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class WorldController {

    private final CountryService countryService;

    @QueryMapping
    public Country getCountry(@Argument String code) {
        return countryService.getCountryByCode(code);
    }
}
