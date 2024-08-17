package dev.antonio3a.worldapi.api.controllers;

import dev.antonio3a.worldapi.api.payloads.CityDto;
import dev.antonio3a.worldapi.domain.services.CityService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.data.web.SortDefault;
import org.springframework.hateoas.PagedModel;
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
    public CityDto getCityById(@PathVariable Integer id) {
        return cityService.getCityById(id);
    }

    @GetMapping(produces = "application/json")
    public PagedModel<CityDto> getCities(@ParameterObject @SortDefault(value = "name,asc") Pageable pageable,
                                         PagedResourcesAssembler assembler) {
        return cityService.getCities(pageable, assembler);
    }
}
