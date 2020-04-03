package com.example.demo.Role;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class RoleModelAssembler implements RepresentationModelAssembler<Role, EntityModel<Role>> {
    @Override
    public EntityModel<Role> toModel(Role role){
        return new EntityModel<>(role,
                linkTo(methodOn(RoleController.class).GetById(role.getId())).withSelfRel(),
                linkTo(methodOn(RoleController.class).GetAll()).withRel("roles"));
    }
}
