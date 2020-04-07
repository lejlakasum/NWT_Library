package com.example.Application.Author;

import com.example.Application.Country.Country;
import com.example.Application.Country.CountryController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class AuthorModelAssembler implements RepresentationModelAssembler<Author, EntityModel<Author>> {

    @Override
    public EntityModel<Author> toModel(Author author) {

        if(!author.getCountry().hasLinks()) {
            author.getCountry()
                    .add(linkTo(methodOn(CountryController.class)
                            .GetById(author.getCountry().getId()))
                            .withSelfRel());

            author.getCountry()
                    .add(linkTo(methodOn(CountryController.class)
                            .GetAll())
                            .withRel("countries"));
        }

        return new EntityModel<>(author,
                linkTo(methodOn(AuthorController.class).GetById(author.getId())).withSelfRel(),
                linkTo(methodOn(AuthorController.class).GetAll()).withRel("authors"));
    }
}
