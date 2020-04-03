package com.example.Application.Copy;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CopyModelAssembler implements RepresentationModelAssembler<Copy, EntityModel<Copy>> {

    @Override
    public EntityModel<Copy> toModel(Copy copy) {

        return new EntityModel<>(copy,
                linkTo(methodOn(CopyController.class).GetById(copy.getId())).withSelfRel(),
                linkTo(methodOn(CopyController.class).GetAll()).withRel("copies"));
    }
}
