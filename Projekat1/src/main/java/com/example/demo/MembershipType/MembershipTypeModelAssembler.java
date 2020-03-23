package com.example.demo.MembershipType;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class MembershipTypeModelAssembler implements RepresentationModelAssembler<MembershipType, EntityModel<MembershipType>> {
    @Override
    public EntityModel<MembershipType> toModel(MembershipType membershipType){
        return new EntityModel<>(membershipType,
                linkTo(methodOn(MembershipTypeContoller.class).GetById(membershipType.getId())).withSelfRel(),
                linkTo(methodOn(MembershipTypeContoller.class).GetAll()).withRel("membershipTypes"));
    }
}
