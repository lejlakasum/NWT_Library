package com.example.Application.Country;

import com.example.Application.Genre.Genre;
import com.example.Application.Genre.GenreController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
class CountryModelAssembler implements RepresentationModelAssembler<Country, EntityModel<Country>> {

    @Override
    public EntityModel<Country> toModel(Country country) {

        return new EntityModel<>(country,
                linkTo(methodOn(CountryController.class).GetById(country.getId())).withSelfRel(),
                linkTo(methodOn(CountryController.class).GetAll()).withRel("countries"));
    }

}