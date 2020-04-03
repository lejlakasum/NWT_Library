package com.example.demo.Fee;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import javax.swing.text.html.parser.Entity;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class FeeModelAssembler implements RepresentationModelAssembler<Fee, EntityModel<Fee>> {

    @Override
    public EntityModel<Fee> toModel(Fee fee){
        return new EntityModel<>(fee,
                linkTo(methodOn(FeeController.class).GetById(fee.getId())).withSelfRel(),
                linkTo(methodOn(FeeController.class).GetAll()).withRel("fees"));
    }

}
