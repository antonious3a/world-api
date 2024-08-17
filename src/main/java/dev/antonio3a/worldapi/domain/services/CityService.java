package dev.antonio3a.worldapi.domain.services;

import dev.antonio3a.worldapi.api.controllers.CityController;
import dev.antonio3a.worldapi.api.payloads.CityDto;
import dev.antonio3a.worldapi.domain.repositories.CityRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
@RequiredArgsConstructor
public class CityService {

    private final CityRepository cityRepository;
    private final ModelMapper modelMapper;

    public CityDto getCityById(Integer id) {
        return modelMapper.map(cityRepository.findById(id).orElseThrow(), CityDto.class)
                .add(linkTo(methodOn(CityController.class).getCityById(id)).withSelfRel())
                .add(linkTo(methodOn(CityController.class).getCities(Pageable.unpaged(), null)).withRel(IanaLinkRelations.COLLECTION));
    }

    public PagedModel<CityDto> getCities(Pageable pageable, PagedResourcesAssembler assembler) {
        return assembler.toModel(cityRepository.findAll(pageable)
                .map(city -> modelMapper.map(city, CityDto.class)
                        .add(linkTo(methodOn(CityController.class).getCities(pageable, assembler)).withSelfRel().withType("GET"))
                )
        );
    }
}
