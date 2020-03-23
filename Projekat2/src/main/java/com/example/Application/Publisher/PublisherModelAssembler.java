package com.example.Application.Publisher;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class PublisherModelAssembler implements RepresentationModelAssembler<Publisher, EntityModel<Publisher>> {

    @Override
    public EntityModel<Publisher> toModel(Publisher publisher) {

        return new EntityModel<>(publisher,
                linkTo(methodOn(PublisherController.class).GetById(publisher.getId())).withSelfRel(),
                linkTo(methodOn(PublisherController.class).GetAll()).withRel("publishers"));
    }
}
