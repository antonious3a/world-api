package dev.antonio3a.worldapi.api.controllers;

import dev.antonio3a.worldapi.domain.entities.City;
import dev.antonio3a.worldapi.domain.services.CityService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/world/api/cities")
@SecurityRequirement(name = "JWT")
public class CityController {

    private final CityService cityService;

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{id}")
    public City getCityById(@PathVariable Integer id) {
        return cityService.getCityById(id);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Page<City> getCities(@RequestParam(defaultValue = "0") Integer page,
                                @RequestParam(defaultValue = "10") Integer size,
                                @RequestParam(defaultValue = "ASC") String sortDirection,
                                @RequestParam(defaultValue = "id") String... sortBy) {
        return cityService.getCities(page, size, sortDirection, sortBy);
    }
}
