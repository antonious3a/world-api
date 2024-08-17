package dev.antonio3a.worldapi.api.controllers;

import dev.antonio3a.worldapi.domain.entities.City;
import dev.antonio3a.worldapi.domain.services.CityService;
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
@RequestMapping("/world/api/cities")
@SecurityRequirements(value = {
        @SecurityRequirement(name = "JWT"),
        @SecurityRequirement(name = "OAUTH2")
})
public class CityController {

    private final CityService cityService;

    @GetMapping(value = "/{id}", produces = "application/json")
    public City getCityById(@PathVariable Integer id) {
        return cityService.getCityById(id);
    }

    @GetMapping(produces = "application/json")
    public Page<City> getCities(@ParameterObject @SortDefault(value = "name,asc") Pageable pageable) {
        return cityService.getCities(pageable);
    }
}
