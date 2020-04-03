package com.example.Application.Genre;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
class GenreModelAssembler implements RepresentationModelAssembler<Genre, EntityModel<Genre>> {

    @Override
    public EntityModel<Genre> toModel(Genre genre) {

        return new EntityModel<>(genre,
                linkTo(methodOn(GenreController.class).GetById(genre.getId())).withSelfRel(),
                linkTo(methodOn(GenreController.class).GetAll()).withRel("genres"));
    }
}
