package dev.antonio3a.worldapi.api.controllers;

import dev.antonio3a.worldapi.api.payloads.CityDto;
import dev.antonio3a.worldapi.api.payloads.CountryDto;
import dev.antonio3a.worldapi.domain.services.CountryService;
import dev.antonio3a.worldapi.infra.exceptions.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Pattern;
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

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/world/api/v1/countries")
@SecurityRequirements(value = {
        @SecurityRequirement(name = "JWT"),
        @SecurityRequirement(name = "OAUTH2")
})
@Tag(name = "Countries", description = "Operations related to countries")
public class CountryController {

    private final CountryService countryService;

    @Operation(summary = "Get a country by its code")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Country found"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Country not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    })
    @GetMapping(value = "/{code}", produces = "application/json")
    public CountryDto getCountryByCode(@PathVariable @Pattern(regexp = "[A-Z]{3}") @Parameter(description = "Three letter country code") String code) {
        return countryService.getCountryByCode(code);
    }

    @GetMapping(produces = "application/json")
    public PagedModel<CountryDto> getCountries(@ParameterObject @SortDefault(value = "code,asc") Pageable pageable,
                                               PagedResourcesAssembler assembler) {
        return countryService.getCountries(pageable, assembler);
    }

    @GetMapping(value = "/{code}/cities", produces = "application/json")
    public List<CityDto> getCitiesByCountryCode(@PathVariable @Pattern(regexp = "[A-Z]{3}") String code) {
        return countryService.getCitiesByCountryCode(code);
    }
}
