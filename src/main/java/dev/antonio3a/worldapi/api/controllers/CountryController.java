package dev.antonio3a.worldapi.api.controllers;

import dev.antonio3a.worldapi.api.payloads.CountryDto;
import dev.antonio3a.worldapi.domain.services.CountryService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import jakarta.validation.constraints.Pattern;
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

    @GetMapping(value = "/{code}", produces = "application/json")
    public CountryDto getCountryByCode(@PathVariable @Pattern(regexp = "[A-Z]{3}") String code) {
        return countryService.getCountryByCode(code);
    }

    @GetMapping(produces = "application/json")
    public Page<CountryDto> getCountries(@ParameterObject @SortDefault(value = "code,asc") Pageable pageable) {
        return countryService.getCountries(pageable);
    }
}
