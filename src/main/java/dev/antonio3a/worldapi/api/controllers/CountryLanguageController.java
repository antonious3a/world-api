package dev.antonio3a.worldapi.api.controllers;

import dev.antonio3a.worldapi.domain.entities.CountryLanguage;
import dev.antonio3a.worldapi.domain.services.CountryLanguageService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.SortDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/world/api/country-languages")
@SecurityRequirements(value = {
        @SecurityRequirement(name = "JWT"),
        @SecurityRequirement(name = "OAUTH2")
})
public class CountryLanguageController {

    private final CountryLanguageService countryLanguageService;

    @GetMapping(value = "/{countryId}", produces = "application/json")
    public List<CountryLanguage> getCountryLanguageByCountryId(@PathVariable String countryId) {
        return countryLanguageService.getCountryLanguageByCountryId(countryId);
    }

    @GetMapping(produces = "application/json")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Page<CountryLanguage> getCountryLanguages(@ParameterObject @SortDefault(value = "countryCode,asc") Pageable pageable) {
        return countryLanguageService.getCountryLanguages(pageable);
    }
}
