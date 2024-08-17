package dev.antonio3a.worldapi.api.controllers;

import dev.antonio3a.worldapi.domain.entities.Country;
import dev.antonio3a.worldapi.domain.services.CountryService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.SortDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/world/api/countries")
@SecurityRequirements(value = {
        @SecurityRequirement(name = "JWT"),
        @SecurityRequirement(name = "OAUTH2")
})
public class CountryController {

    private final CountryService countryService;

    @GetMapping(value = "/{id}", produces = "application/json")
    public Country getCountryById(@PathVariable String id) {
        return countryService.getCountryById(id);
    }

    @GetMapping(produces = "application/json")
    public Page<Country> getCountries(@ParameterObject @SortDefault(value = "code,asc") Pageable pageable) {
        return countryService.getCountries(pageable);
    }
}
